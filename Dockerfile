FROM maven:latest AS builder

COPY pom.xml /usr/src/app/
COPY src /usr/src/app/src/

WORKDIR /usr/src/app

RUN mvn package

FROM openjdk:21-slim

EXPOSE 8080

COPY --from=builder /usr/src/app/target/Wishlist-0.0.1-SNAPSHOT.jar /usr/src/app/Wishlist-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "/usr/src/app/Wishlist-0.0.1-SNAPSHOT.jar"]
