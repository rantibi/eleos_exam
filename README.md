# Jennifer's online clinic
This task is the backend implementation for the exam [Jennifer's online clinic](https://www.notion.so/Jennifer-s-online-clinic-4082662698fd47d2845493a457459615)

## Setup instruction
1. Install [OpenJDK 1.8](https://openjdk.java.net/install/)
2. Install [maven](https://maven.apache.org/install.html) 
3..Update in zoom.api-key in `/src/main/resources/application.properties`
4. Navigate to `eleos_exam` directory
5. Execute `mvn clean install`
6. Execute `java -jar target/eleos.exam-1.0.jar`
7. Check if the following call return [http://127.0.0.1:8080/api/v1/meetings](http://127.0.0.1:8080/api/v1/meetings)

## H2 console
In order to browse H2 DB go to [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/)  
you can find the connection string, user and password in `application.properties` file
