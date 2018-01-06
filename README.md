# GemDev

[![Build Status](https://travis-ci.org/mkarpisek/gemdev.svg?branch=master)](https://travis-ci.org/mkarpisek/gemdev)
[![codecov](https://codecov.io/gh/mkarpisek/gemdev/branch/master/graph/badge.svg)](https://codecov.io/gh/mkarpisek/gemdev)
[![sonarcloud](https://sonarcloud.io/api/badges/gate?key=net.karpisek.gemdev%3Anet.karpisek.gemdev.all)](https://sonarcloud.io/dashboard?id=net.karpisek.gemdev%3Anet.karpisek.gemdev.all)

Eclipse development environment for GemStone/S

## Getting Started

Build and run with all tests:
```
mvn clean verify
```

### Prerequisites

Java 8, GemStone/64 2.4.x, Eclipse Oxygen 4.7

## Running the tests

Build and run (only) unit tests:
```
mvn clean verify -P unit
```

Build and run (only) integration tests (needs GemStone VM with running db and broker server):
```
mvn clean verify -P integration
```

Build without running tests:
```
mvn clean verify -DskipTests
```

## License

This project is licensed under the EPL 1.0 License - see the [LICENSE.md](LICENSE.md) file for details
