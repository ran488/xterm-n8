# Sample App 

Originally written as a prototype to get view-only access to XML requests and responses for a CRM system, and to eventually submit leads into the system. 
All business logic, company-specific info, and references to the back-end systems has been stripped out so I can have a working sample of this technology stack for future endeavors.

## Technology Stack

- Java 8
- Spring Boot
- Spring Integration
- Spring JDBC
- Spring Web (REST endpoints)
- H2 embedded database
- Gradle build tool
- Project Lombok
- AngularJS
- HTML/CSS/JavaScript


## Building and Running

- Regenerate Eclipse project metadata
```
gradlew eclipse
```
- Clean and build
```
gradlew clean build
```
- Run from Gradle
```
gradlew bootRun
```
- Run from command-line (after building JAR) Windows
```
java -jar build\libs\prototype-app-1.0.jar
```
- Run from command-line (after building JAR) *nix
```
java -jar ./build/libs/prototype-app-1.0.jar
```

