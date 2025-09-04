FROM openjdk:17-jdk-slim
WORKDIR .
COPY build/libs/newsTHINKY-BE-0.0.1-SNAPSHOT.jar app.jar
COPY rss_news.csv ./rss_news.csv
ENTRYPOINT ["java", "-jar", "app.jar"]
