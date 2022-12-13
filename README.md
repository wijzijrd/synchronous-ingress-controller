# Overview

This project is a proof of concept for an synchronous to asynchronous API controller. The project seems to integrate into a fully event driven architecture in a synchronous manner. The main use case would be the transition between event driven and synchronous REST implementations. Allowing for the backend to be transformed while waiting for the frontend to have capacity for changes.

****

## Pre-requisites
1. Docker Desktop
2. Java 17
3. Maven

## Setup
Startup kafka cluster
```shell
cd kafka-impl
docker-compose up -d
```

Install Redis from latest DockerHub Image
```shell
docker run --name redis-impl -p 6379:6379 -d redis
```

## Understanding this project


## Kafka Implementation
Houses the actual Kafka implementation. This is where events will be published.

