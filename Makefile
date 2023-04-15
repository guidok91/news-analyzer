.PHONY: help
help:
	@grep -E '^[a-zA-Z0-9 -]+:.*#'  Makefile | while read -r l; do printf "\033[1;32m$$(echo $$l | cut -f 1 -d':')\033[00m:$$(echo $$l | cut -f 2- -d'#')\n"; done

.PHONY: 
compile: # Compile app code.
	sbt compile

.PHONY: 
package: # Package app code.
	sbt package

.PHONY: 
test: # Run tests.
	sbt test

.PHONY: 
format: # Format app code.
	sbt scalafmtAll

.PHONY: 
format-check: # Check code format.
	sbt scalafmtCheckAll

.PHONY: 
run: # Run app.
	sbt run

.PHONY: 
clean: # Clean auxiliary files.
	sbt clean

.PHONY: kafka-up
kafka-up: # Spin up local Kafka instance with Docker. 
	docker-compose up -d

.PHONY: kafka-down
kafka-down: # Tear down local Kafka instance. 
	docker-compose down

.PHONY: kafka-create-topic
kafka-create-topic: # Create Kafka topic.
	docker exec broker \
	kafka-topics \
	--bootstrap-server broker:9092 \
	--create \
	--topic tweets.sentiment.v1 \
	--partitions 6

.PHONY: kafka-read-test-events
kafka-read-test-events: # Read and display events.
	docker exec --interactive --tty schema-registry \
	kafka-avro-console-consumer \
	--topic tweets.sentiment.v1 \
	--bootstrap-server broker:9092 \
	--property schema.registry.url=http://localhost:8081 \
	--from-beginning
