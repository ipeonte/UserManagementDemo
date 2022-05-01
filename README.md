# Demo application for User Management

## High Level Component Diagram

## API List

- /api/v1/upsertUser API to update user if exists else create a new
- /api/v1/isUserExists API which takes input as email and returns appropriate status
- /api/v1/listUsers API to return list of all users

## Build platform independent package

  mvn clean package
  
## Build (PROD) package with PostgreSQL support

  mvn clean package -P postgre
  
## Execute

  java -jar target/user-mgmt-rest-1.0.0.jar
  
Please note that any production build requires external application.properties with jdbc configuration and platform independent package requires database driver available in system path 
  
## application.properties examples

### PostgreSQL

  spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
  spring.datasource.username=<user_name>
  spring.datasource.password=<user_password>
  spring.datasource.hikari.schema=<db_name>

### MySQL

  spring.datasource.driver-class-name=com.mysql.jdbc.Driver
  spring.datasource.url=jdbc:mysql://localhost:3306/<db_name>
  spring.datasource.username=<user_name>
  spring.datasource.password=<user_password>