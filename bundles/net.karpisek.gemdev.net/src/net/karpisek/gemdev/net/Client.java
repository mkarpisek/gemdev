/*******************************************************************************
 * Copyright (c) 2008, 2018 Martin Karpisek.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Martin Karpisek - initial API and implementation
 *******************************************************************************/
package net.karpisek.gemdev.net;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * Common client implementation for connecting to broker and session servers.
 */
public abstract class Client {
	/**
	 * Number of retries client will do in case executed action is idempotent.
	 */
	public static final int DEFAULT_RETRY_COUNT = 3;

	/**
	 * Encoding used by all requests by this client
	 */
	private static final String REQUEST_ENCODING = "ISO-8859-1"; //$NON-NLS-1$

	/**
	 * Conent type of all requests sent by this client
	 */
	private static final String REQUEST_CONTENT_TYPE = "text/plain"; //$NON-NLS-1$

	/**
	 * first line of server error response
	 */
	private static final String SERVER_ERROR_HEADER = "ServerError";

	/**
	 * first line of unhandled error response
	 */
	private static final String UNHANDLED_ERROR_HEADER = "UnhandledError";

	private final String hostName;
	private final int port;

	protected AtomicBoolean closed;
	protected final MultiThreadedHttpConnectionManager httpConnectionManager;
	protected final HttpClient httpClient;

	private final boolean enableTracing;

	public Client(final String hostName, final int port, final boolean enableTracing) {
		this.hostName = hostName;
		this.port = port;
		this.closed = new AtomicBoolean();

		this.httpConnectionManager = newConnectionManager();
		this.httpClient = newHttpClient();
		this.enableTracing = enableTracing;
	}

	/**
	 * Performs clean up of all used resources. Client will reject all requests which will be submitted after this call. Calling this method on already closed
	 * client does nothing.
	 */
	public void close() {
		if (isClosed()) {
			return;
		}
		httpConnectionManager.shutdown();
	}

	/**
	 * Answer host name of server to which is/should be this client connected
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * Answer port number of server to which is/should be this client connected
	 */
	public int getPort() {
		return port;
	}

	public String getUrlString() {
		return String.format("http://%s:%d", getHostName(), getPort()); //$NON-NLS-1$ ;
	}

	/**
	 * Answers if this client is closed and so can not accept any new requests.
	 */
	public boolean isClosed() {
		return closed.get();
	}

	private boolean isTracingEnabled() {
		return enableTracing;
	}

	/**
	 * Sends to server HTTP Post message and fetches the answer.
	 * 
	 * @param actionName of this request
	 * @param request string representation of request which has to be conforimng to interface implemented on server
	 * @param canBeRepeated true in case request is idempotent and so in case of transport error can be repeated without any side effects
	 * @return answer as string
	 */
	protected String executePostMethod(final String actionName, final String request, final boolean canBeRepeated) {
		if (isClosed()) {
			throw new IllegalStateException(String.format("Client %s:%d is already closed.", getHostName(), getPort()));
		}

		final int maxGlobalRetry = canBeRepeated ? DEFAULT_RETRY_COUNT : 1;

		for (int globalRetry = 0; globalRetry < maxGlobalRetry; globalRetry++) {
			final PostMethod method = new PostMethod(getUrlString());
			try {
				method.setRequestEntity(new StringRequestEntity(request, REQUEST_CONTENT_TYPE, REQUEST_ENCODING));

				// Provide custom retry handler is necessary
				int retryCount = 0;
				if (canBeRepeated) {
					retryCount = DEFAULT_RETRY_COUNT;
				}
				method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(retryCount, false));

				// Execute the method.
				final long t0 = System.currentTimeMillis();
				final int statusCode = httpClient.executeMethod(method);
				final long t1 = System.currentTimeMillis();

				if (statusCode != HttpStatus.SC_OK) {
					throw new RuntimeException(String.format("HTTP Error: %s", method.getStatusLine().toString())); //$NON-NLS-1$
				}

				final String responseString = method.getResponseBodyAsString();
				final long t2 = System.currentTimeMillis();

				checkServerError(actionName, request, responseString);
				checkUnhandledError(actionName, request, responseString);

				final long t3 = System.currentTimeMillis();

				if (isTracingEnabled()) {
					NetPlugin.getDefault().logInfo("%s total=%f executeMethod=%f getResponseBodyAsString=%f asRespone=%f%n", //$NON-NLS-1$
							actionName, (t3 - t0) / 1000.0, (t1 - t0) / 1000.0, (t2 - t1) / 1000.0, (t3 - t2) / 1000.0);
				}

				return responseString;
			} catch (final HttpException e) {
				throw new RuntimeException(e);
			} catch (final IOException e) {
				if (globalRetry >= maxGlobalRetry - 1) {
					final Throwable t = e.getCause() == null ? e : e.getCause();

					throw new FatalTransportException(actionName, t.getMessage(), request, getHostName(), getPort(), t);
				} else {
					NetPlugin.getDefault().logInfo(String.format("Retrying action %s after transport error: %s%nRequest:%n%s%n", //$NON-NLS-1$
							actionName, e.getMessage(), request), e.getCause());

				}
			} finally {
				method.releaseConnection();
			}
		}
		throw new ActionException(actionName, "", request, null);
	}

	protected void checkServerError(final String actionName, final String request, final String responseString) {
		if (!responseString.startsWith(SERVER_ERROR_HEADER)) {
			return;
		}
		throw new ServerError(actionName, responseString.substring(SERVER_ERROR_HEADER.length() + 1), request);
	}

	protected void checkUnhandledError(final String actionName, final String request, final String responseString) {
		if (!responseString.startsWith(UNHANDLED_ERROR_HEADER)) {
			return;
		}

		int i = UNHANDLED_ERROR_HEADER.length() + 1;
		final String sizesString = responseString.substring(i, i = responseString.indexOf('\n', i));
		final String[] sizes = sizesString.split(" "); //$NON-NLS-1$
		final int messageSize = Integer.valueOf(sizes[0]);
		final int requestSize = Integer.valueOf(sizes[1]);
		final int gsStackReportSize = Integer.valueOf(sizes[2]);

		final String messageString = responseString.substring(i + 1, i = i + messageSize + 1);
		final String requestString = responseString.substring(i + 1, i = i + requestSize + 1);
		final String gsStackReportString = responseString.substring(i + 1, i = i + gsStackReportSize + 1);

		throw new UnhandledErrorException(actionName, messageString, request, gsStackReportString);
	}

	/**
	 * Answer new http connection manager object used by this client for creating connections
	 */
	protected MultiThreadedHttpConnectionManager newConnectionManager() {
		final MultiThreadedHttpConnectionManager m = new MultiThreadedHttpConnectionManager();
		final HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, 10);
		m.setParams(params);
		return m;
	}

	/**
	 * answer new http client object used by all requests sent by this client
	 */
	protected HttpClient newHttpClient() {
		return new HttpClient(httpConnectionManager);
	}
}
