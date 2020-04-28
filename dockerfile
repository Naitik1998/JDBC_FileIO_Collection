FROM openjdk:8 
EXPOSE 8080 
ADD target/ComicApp-0.0.1-SNAPSHOT-jar-with-dependencies.jar ComicApp-0.0.1-SNAPSHOT-jar-with-dependencies.jar 
ENTRYPOINT ["java","-jar","/ComicApp-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
