# change H
Write a password validation service, meant to be configurable via IoC (using dependency injection engine of your choice). The service is meant to check a text string for compliance to any number of password validation rules. The rules currently known are: 

1) Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each.
2) Must be between 5 and 12 characters in length. 
3) Must not contain any sequence of characters immediately followed by the same sequence. 

# System Requirments

1) Java/Jdk 1.8
2) Maven 3.3.x

# Instructions

1) mvn clean install 
   -- test cases will run as part of this build.
2) this is a spring boot project, so a jar will be build with an embedded tomcat server.
2) cd target (expecting no errors)
3) java -jar target/validator-1.0.0.jar
   alternatively we can also start the tomcat in debug mode:
   java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 validator-1.0.0.jar
4) This will start an embedded tomcat on port 9064
5) curl -v GET http://localhost:9064/change/validate/password/triedout1
6) Code should spellout more details and testcases.
