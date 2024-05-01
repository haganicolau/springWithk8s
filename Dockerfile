FROM openjdk:17-alpine

RUN apk add --no-cache bash

WORKDIR /app
COPY target/*.jar app.jar

ENV PORT 8081
EXPOSE 8081

CMD ["tail", "-f", "logs/*.log"]
ENTRYPOINT ["java", "-jar", "app.jar"]