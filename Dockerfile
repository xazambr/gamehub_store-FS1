FROM maven:3-eclipse-temurin-21 AS build
ARG MODULO
WORKDIR /app
COPY . .
RUN mvn -B -pl ${MODULO} -am package -DskipTests

FROM eclipse-temurin:21-jre
ARG MODULO
WORKDIR /app
COPY --from=build /app/${MODULO}/target/*.jar app.jar
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=70"
ENTRYPOINT ["java", "-jar", "app.jar"]