# FreightmateDemo

### Application development environment

The application is developed using
	
	1. Java 17
	2. SpringBoot
	3. Junit + Mockito
	4. Swagger

### How to run the application with maven build (Recommended)

To run the application the following steps need to processed
	
	./mvnw clean install	 -DskipTests  -- if test cases run not required
	./mvnw clean install              -- with test case
	
The above command will build the application


The above command start the application on `9129` port.

### Testing application with sample data
 
	curl --location --request POST 'http://localhost:9129/api/v1/suburbs' --header 'Content-Type: application/json' --data-raw '{ "postcode":2849,"suburbName": "DABEE, NSW"}'

### Assumption for the application
The application is developed based on assumptions

	1.An API that allows clients to retrieve the suburb information by postcode.
	2.An API that allows mobile clients to retrieve a postcode given a suburb name.
	3.A secured API to add new suburb and postcode combinations 
	4.All parameters in JSON body is required.
	5.Postcode must be between 200 and 9999.
	6.Suburb name must have at least 3 characters 

### Database Structure

The application is using MySQL

	CREATE TABLE `suburb_postcode` (
	`postcode` int NOT NULL,
	`suburb_name` varchar(255) NOT NULL,
	PRIMARY KEY (`postcode`,`suburb_name`)
	);

	
	
	 	