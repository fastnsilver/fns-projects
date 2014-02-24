# Loan Calculator
A collection of projects meant to demonstrate an online loan calculator. 

Comprised of a model, REST service, and UI variants.

## Pre-requisites
* [Java SDK 1.7.x](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
* [Maven 3.1.x](http://maven.apache.org/download.cgi)

## Technology Stack

### Shared
* Lombok 1.12.4
* Jackson 1.9.13

### Server-side
* Spring Framework 4.0.x.RELEASE
* Spring Boot 1.0.0.BUILD-SNAPSHOT
* Spring Actuator 1.0.0.BUILD-SNAPSHOT
* Spring Security 3.2.0.RELEASE
* [Vladimir Dzhuvinov's CORSFilter](http://software.dzhuvinov.com/cors-filter.html)
* Gemfire 7.0.x

### Client-side

#### GWT
* GWT 2.6.0
* RestyGWT 1.4
* MVP4G 1.5.0-SNAPSHOT

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


## To interact with the service 
From a shell, make a GET request to	

	{host}:{port}/schedule/get/debtor/{debtor}/amount/{amount}/interest/{interest}/years/{years}/compounded/{compounded}

e.g., 

	$ curl http://localhost:9000/schedule/get/debtor/citi/amount/1000/interest/11.5/years/2/compounded/MONTHLY | jq '.'

In the example above, available variants for calculation of compound interest are: 

	MONTHLY, QUARTERLY, SEMIANNUALLY, ANNUALLY

Also, [jq](http://stedolan.github.io/jq/) is a JSON command-line processor, it may be installed on Mac OSX with [Homebrew](http://brew.sh/)

	$ brew install jq
	
	
## To launch the UI
For now, only a GWT UI variant is implemented.  The simplest way to get up-and-running from a shell is...

	$ cd loan/calculator-gwt-ui
	$ mvn gwt:run

## To interact with the UI
Launch your favorite browser and visit

	http://127.0.0.1:8888/Application.html?gwt.codesvr=127.0.0.1:9997

You will be prompted to install the [GWT Developer Plugin](http://www.gwtproject.org/doc/latest/DevGuideCompilingAndDebugging.html#launching_in_dev_mode) (if it isn't already installed).

Fill in the form and click the Send button to obtain the loan schedule for your input.  The Schedule underneath the form is a [DisclosurePanel](http://www.gwtproject.org/javadoc/latest/com/google/gwt/user/client/ui/DisclosurePanel.html).

## Known Issues
* Loan schedule results are [clipped](https://github.com/fastnsilver/fns-projects/issues/10) when # of periods > 25.
* [Integration](https://github.com/fastnsilver/fns-projects/issues/9) with Spring's [CsrfFilter](http://docs.spring.io/spring-security/site/docs/3.2.1.CI-SNAPSHOT/apidocs/org/springframework/security/web/csrf/CsrfFilter.html) is incomplete.  GWT UI will return a 403 if secure profile is used.

## Planned UI variants
* Vaadin
* Spring Thymeleaf.
