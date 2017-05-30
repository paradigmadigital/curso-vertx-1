#!/usr/bin/env bash
java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar target/example3-api-rest-fat.jar -cluster goku -conf src/config/config_cluster.json
