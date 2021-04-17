FROM maven:3.6.3-jdk-8
ARG SERVER_HOME="/srv"
ARG VERSION="0.0.4"
RUN mkdir -p "$SERVER_HOME/src"

ADD ./src "$SERVER_HOME/src"
COPY pom.xml $SERVER_HOME

RUN mvn -Drevision=$VERSION package -f $SERVER_HOME/

ENV SERVER_HOME ${SERVER_HOME}
ENV VERSION ${VERSION}
CMD java -jar "${SERVER_HOME}/target/server-${VERSION}.jar"