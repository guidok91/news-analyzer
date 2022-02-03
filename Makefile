SHELL=/bin/bash

compile:
	sbt compile

package:
	sbt package

test:
	sbt test

format:
	sbt scalafmtAll

run:
	sbt run

clean:
	sbt clean
