SHELL=/bin/bash

compile:
	sbt compile

package:
	sbt package

test:
	sbt test

run:
	sbt "run ${x}"
