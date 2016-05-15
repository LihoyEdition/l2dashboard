# L2Dashboard
**L2Dashboard** is the monitoring and management system for a Lineage 2 server 

**Modules**:
- api - a set of interfaces that need to be implemented to integrate with server L2Dashboard
- web - RESTful-service that provides data from the api-module as a JSON
- ui - web-based GUI that interacts with the web-module
- stub - dummy module that contains api implementation using hard-coded data

**Technology stack**:
- Java 8
- Spring (Core, MVC, Boot)
- AngularJS + angular-nvd3
- Bootstrap 3
- Maven

**How to run**:
- build project using _mvn package_ (or _mvn install_) command inside root directory
- run jar file of web module using _java -jar path_to_file.jar_ command
- go to http://localhost:8080/
