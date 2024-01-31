FROM openjdk:17
WORKDIR /app
COPY build/libs/board-0.0.1-SNAPSHOT.jar board.jar
CMD ["java", "-jar", "board.jar"]