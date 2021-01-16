FROM tomcat:jdk15-openjdk-oracle
ADD target/murabi.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]
