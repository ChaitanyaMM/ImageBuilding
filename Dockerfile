FROM 		tomcat:8.0.21-jre8

MAINTAINER 	Chaitanya
ADD target/ImageBuilding.jar ImageBuilding.jar


ENTRYPOINT ["java", "-jar", "/ImageBuilding.jar"]


EXPOSE 8080


 