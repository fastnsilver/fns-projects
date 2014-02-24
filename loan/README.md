# Loan Calculator
A collection of projects meant to demonstrate an online loan calculator. 

Comprised of a model, REST service, and UI variants.

## Pre-requisites
* [Java SDK 1.7.x](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
* [Maven 3.1.x](http://maven.apache.org/download.cgi)

## Server-side Technology Stack
* Spring Framework 4.0.x.RELEASE
* Spring Boot 1.0.0.BUILD-SNAPSHOT
* Spring Actuator 1.0.0.BUILD-SNAPSHOT
* Spring Security 3.2.0.RELEASE
* [Vladimir Dzhuvinov's CORSFilter](http://software.dzhuvinov.com/cors-filter.html)
* Gemfire 7.0.x

## Installation
[Clone](https://github.com/fastnsilver/fns-projects.git) or [download](https://github.com/fastnsilver/fns-projects/archive/master.zip) fns-projects from Github.

	$ cd loan
	$ mvn clean install

## To launch the service
From a shell...

	$ cd loan/calculator-service/target
	$ java -jar loan-calculator-service-1.0.0-SNAPSHOT.jar

... or if using Eclipse IDE with Spring plugin, configure a Spring Boot launch item for the loan-calculator-service targeting

	io.fns.Application

## To launch the UI
For now, only a GWT UI variant is implemented.  The simplest way to get up-and-running from a shell is...

	$ cd loan/calculator-gwt-ui
	$ mvn gwt:run

## Known Issues
* [Integration](https://github.com/fastnsilver/fns-projects/issues/9) with Spring's [CsrfFilter](http://docs.spring.io/spring-security/site/docs/3.2.1.CI-SNAPSHOT/apidocs/org/springframework/security/web/csrf/CsrfFilter.html) is incomplete.  GWT UI will return a 403.

## Planned UI variants
* Vaadin
* Spring Thymeleaf.
