mvn archetype:generate -DinteractiveMode=false -DartifactId=undertowweb -DarchetypeArtifactId=maven-archetype-webapp -DgroupId=com.wayne

mvn clean install dependency:sources dependency:resolve -Dclassifier=javadoc -DskipTests=true

sudo mvn exec:java

java -jar target/undertow-1.0-SNAPSHOT.jar 

java -jar target/undertowweb.jar

curl -v http://localhost:8081/context/path2/get1?p0=abc&p1=bcd -H "Content-Type: application/json"

curl -v http://localhost:8081/context/path2/post1 -H "Content-Type: application/json" -H "Accept:application/json" -d '{ "hello":"data" }\r\n'


