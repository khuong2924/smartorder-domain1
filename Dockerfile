FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
RUN apt-get update && apt-get install -y curl
COPY --from=build /app/target/domain1-0.0.1-SNAPSHOT.jar app.jar

# Thêm các biến môi trường mặc định
ENV JWT_SECRET=8K9sJ2mPqX7vL4rT5nY8uW3iB6oZ9cA1dF4gH7jK
ENV JWT_EXPIRATION=86400000

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]

