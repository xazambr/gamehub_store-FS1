# Una sola receta para los 13 servicios del ecosistema. Se elige cuál construir con el argumento MODULO:
#   docker build --build-arg MODULO=product-service -t gamehub/product-service .
# docker-compose.yml la usa para todos.

# Etapa 1: compila el módulo pedido y lo que necesita (comun) con Maven
FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
ARG MODULO
# La caché de ~/.m2 se comparte entre las 13 construcciones: las dependencias se descargan una sola vez.
# sharing=locked hace que las construcciones usen la caché por turnos, sin pisarse.
RUN --mount=type=cache,target=/root/.m2,sharing=locked mvn -q -B -pl ${MODULO} -am package -DskipTests

# Etapa 2: imagen liviana solo con el JRE y el .jar del servicio
FROM eclipse-temurin:21-jre
ARG MODULO
WORKDIR /app
COPY --from=build /app/${MODULO}/target/${MODULO}.jar app.jar
# Render Free entrega 512 MB: la JVM usa como máximo el 70 % de la memoria del contenedor
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=70"
ENTRYPOINT ["java", "-jar", "app.jar"]