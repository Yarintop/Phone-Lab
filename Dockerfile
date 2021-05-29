
FROM maven:3.6.3-jdk-8

ARG SERVER_HOME="/srv"
ARG VERSION="0.0.5"

ENV SERVER_HOME ${SERVER_HOME}
ENV VERSION ${VERSION}

RUN mkdir -p "$SERVER_HOME/src"

COPY ./src "$SERVER_HOME/src"
COPY pom.xml $SERVER_HOME

RUN mvn -Drevision=$VERSION package -Dmaven.test.skip=true -f $SERVER_HOME/

EXPOSE 8010

CMD java -Dspring.data.mongodb.host="mongo" \ 
         -jar "${SERVER_HOME}/target/server-${VERSION}.jar"




