#!/usr/bin/env bash
mvn clean package -DskipTests 
java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar target/example3-api-rest-fat.jar -conf target/classes/config.json  
