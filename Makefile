LOCAL_DC_FILE := $(CURDIR)/server/docker-compose-local.yml

NOW := $(shell date +"%Y%m%dT%H%M%S")
TAG ?= $(NOW)

BUILD_PATH ?= $(CURDIR)/sample
TARGET ?= sample

up:
	@docker-compose -f $(LOCAL_DC_FILE) up -d

down:
	@docker-compose -f $(LOCAL_DC_FILE) down

build:
	./gradlew clean build
.PHONY: build

build-docker: build
	cd $(BUILD_PATH) && \
	docker build \
	--build-arg APP_NAME=sample \
	--tag $(TARGET) \
	.
.PHONY: build-docker

default: up