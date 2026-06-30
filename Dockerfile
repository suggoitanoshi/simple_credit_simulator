FROM docker.io/gradle:9-jdk17 AS builder
WORKDIR /app

COPY gradlew settings.gradle.kts ./
COPY app/build.gradle.kts ./app/
COPY gradle ./gradle
RUN ./gradlew dependencies --no-daemon

COPY . .
RUN ./gradlew shadowJar --no-daemon

FROM docker.io/eclipse-temurin:17-jre-jammy
WORKDIR /app

COPY --from=builder /app/app/build/libs/app-all.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
