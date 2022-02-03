SHELL=/bin/bash

compile:
	sbt compile

package:
	sbt package

test:
	sbt test

format:
	sbt scalafmtAll

format-check:
	sbt scalafmtCheckAll

run:
	sbt run

clean:
	sbt clean
