FROM curlimages/curl as otel-agent

WORKDIR /agent

RUN curl -L -O https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.1.0/opentelemetry-javaagent.jar

FROM amazoncorretto:17-alpine

ARG PORT=10000
ARG APP_NAME=my-app

ARG JAR_NAME=sample
ARG FROM_JAR=build/libs/${JAR_NAME}.jar

ARG AGENT_NAME=opentelemetry-javaagent
ARG FROM_AGENT_JAR=/agent/${AGENT_NAME}.jar
ARG OTLP_ENDPOINT=http://localhost:4318

COPY --from=otel-agent ${FROM_AGENT_JAR} ${AGENT_NAME}.jar
COPY ${FROM_JAR} ${APP_NAME}.jar

ENV JAVA_TOOL_OPTIONS="\
-javaagent:/${AGENT_NAME}.jar \
-Dotel.instrumentation.spring-boot-actuator-autoconfigure.enabled=true \
-Dotel.exporter.otlp.endpoint=${OTLP_ENDPOINT} \
-Dotel.resource.attributes=service.name=${APP_NAME} \
"

EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar", "${APP_NAME}.jar"]