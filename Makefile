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
