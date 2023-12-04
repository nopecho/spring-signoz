FROM curlimages/curl as agent

WORKDIR /agent

RUN curl -L -O https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/1.32.0-alpha/download/opentelemetry-javaagent.jar

FROM amazoncorretto:17-alpine
ARG PORT=10000
ARG APP_NAME
ARG FROM_JAR=build/libs/${APP_NAME}.jar

COPY --from=agent /agent /agent
COPY ${FROM_JAR} app.jar

EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]