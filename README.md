# Spring Boot Batch application

## About
This is a simple demo application for show casing the usage of **[Spring Batch](https://spring.io/projects/spring-batch)** with **[Spring Boot](https://spring.io/projects/spring-boot)**.

## Requirements
This demo is built with with Spring boot 2.4.5.

## Build instructions
Run the below command navigating to the application root folder

```java
mvn clean install
```
Once successfully built, goto application main class and run it as java application.

## Application Core Components

```bash

BatchConfiguration.java -> Configuration file for creating the required beans for Batch processing

BatchItemReader.java -> This is for fetching the required data from upstream API

BatchItemProcessor.java -> This is for checking, if the prescription can be flagged based on the check-in time

BatchItemWriter.java -> This is for sending the processed data to downstream API

JobCompletionNoticiationListener.java -> This is for checking the job completion status

ApiController.java -> Dummy controller for replicating batch steps of calling upstream/downstream API

```
## Important configuration

Added scheduler which executes for every 1 minute. Also disabled the job execution during the appplication startup using the below configuration.

```bash
## To disable the job execution during the application startup
spring.batch.job.enabled=false
```

## Author

**Mahesh Kumar Gutam**

* [Github](https://github.com/MaheshIare)