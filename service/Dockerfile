FROM openjdk:11.0.4-jre-slim

ARG JAR_FILE

RUN  apt-get update && apt-get install -y wget
RUN mkdir newrelic && wget https://download.newrelic.com/newrelic/java-agent/newrelic-agent/5.3.0/newrelic-agent-5.3.0.jar -O newrelic/newrelic.jar

COPY newrelic.yml newrelic/newrelic.yml
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT exec java -XX:MaxRAMPercentage=80 -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar app.jar

HEALTHCHECK --start-period=300s \
  CMD wget --quiet --tries=1 --spider --timeout=30 http://localhost:8080/actuator/health || exit 1
