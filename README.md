## Description

Blog is an educational console CRUD application that has the following entities: 

* Writer
* Post
* Label

For storing mentioned above entities JSON files are used.

## Requirements

To build & run the application you need Java-11 and Maven-3.8.5 installed.

## Build and Run the Application

Use Maven command to create single JAR file with all dependencies:

`mvn clean package`

The JAR file with the '-shaded' suffix will appear in directory with build artifacts.

To run JAR file use the command bellow:

`java -jar <filename>.jar`