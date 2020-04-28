FROM openjdk:8 
EXPOSE 8080 
ADD target/WebApp-0.0.1-SNAPSHOT.war WebApp-0.0.1-SNAPSHOT.war 
ENTRYPOINT ["java","-jar","/WebApp-0.0.1-SNAPSHOT.war"]
