# webshop
This is the backend part of an REST applicaton. Wrote in java, implemented with thorntail.

# Structure

## Database
You can find the "webshopdb.sql" file in the root. This is the database. A simple mysql scheme with only 3 tables. 
If you run apache and mysql servers with xampp for example you can import it.
If you could not import, you can create it on by own in phpmyadmin or workbench, but pay attention the schema name, tables name and columns.

## Structure
The RestAppliction make the connection to the database.
You can find the endpoints under the controller folder.
The controllers calls services functions when the frontend reach the endpoints.
The services managed the database queries.
Under the model you can find the representation of data types. 3 main data type: User, Product and Address.

## Run it
Type to the terminal "mvn clean install" and then "java -jar target/webshop-thorntail.jar"
The application will run on the http://localhost:8080 port.

