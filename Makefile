.PHONY: help up down build run test clean logs local

help:
	@echo "Available targets:"
	@echo "  help   - Show this help message"
	@echo "  up     - Start Docker containers (docker compose up -d)"
	@echo "  down   - Stop and remove Docker containers (docker compose down)"
	@echo "  build  - Build the Maven project"
	@echo "  run    - Run the built JAR"
	@echo "  test   - Run Maven tests"
	@echo "  clean  - Clean Maven project and remove target directory"
	@echo "  logs   - Show Docker container logs"
	@echo "  local  - Run the app locally with Maven (skip tests)"

up:
	docker compose up -d

down:
	docker compose down

build:
	mvn clean package -DskipTests

run:
	java -jar target/ClusteredData-0.0.1-SNAPSHOT.jar

test:
	mvn test

clean:
	mvn clean
	rm -rf target

logs:
	docker compose logs -f

local:
	mvn spring-boot:run -Dspring-boot.run.profiles=local -DskipTests
