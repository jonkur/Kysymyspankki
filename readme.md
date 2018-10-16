# Kysymyspankki
This is a simple Java web application that I built as part of a university course on databases.

## Setting it up
This project is meant to be run on Heroku, but it can be started on local configuration for testing purposes.
Here's how you can do that:

### Prerequisities
This project uses Maven. Make sure you have Maven 3.5.2 (or later) installed before proceeding.
You will also need SQLite installed for creating a database.

### Install
Install dependencies & build the project, in project directory:
```
mvn install
```
### Create SQLite database
Create a new SQLite database, preferable in the project directory
You will then need to create tables for the database you just created. The app does not currently support automatic schema migration, so it needs to be done by hand. There is a file named "dbschema" included with the project, you can simply copy the contents of that file and paste them to your SQLite CLI. NOTE: Only cope the lines above the "//Postgres" -comment.

### Run
Once your database is up, you can run the app by typing:
```
java -jar target/Kysymyspankki-1.0-SNAPSHOT-jar-with-dependencies.jar (path-to-db)
```

App should now be up and running, navigate to localhost:4567 with your browser to test it.
