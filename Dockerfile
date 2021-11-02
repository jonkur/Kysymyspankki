FROM maven:3.8.2-jdk-8

EXPOSE 4567

WORKDIR /usr/src/Kysymyspankki/
COPY . .

RUN mvn install

RUN apt update
RUN apt install sqlite3
RUN sed '1,31!d' dbschema > dbschema_redacted
RUN sqlite3 db.sqlite < dbschema_redacted

CMD ["java", "-jar", "target/Kysymyspankki-1.0-SNAPSHOT-jar-with-dependencies.jar", "db.sqlite"]
