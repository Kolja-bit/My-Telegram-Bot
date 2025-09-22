FROM openjdk:17-oracle
WORKDIR /app
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true
COPY target/My-Telegram-Bot-0.0.1-SNAPSHOT.jar My-Telegram-Bot.jar
EXPOSE 8090
CMD ["java","-jar","My-Telegram-Bot.jar"]