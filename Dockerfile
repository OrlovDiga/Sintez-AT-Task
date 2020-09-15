FROM tomcat:latest
COPY ./target/Short-Uri-Web-Service.war /usr/local/tomcat/webapps/
EXPOSE 8080