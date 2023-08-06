Docker-Doobie Database Instructions:

1. Open terminal at /typelevel-project:
    run `docker compose up`

2. Open a new terminal at /typelevel-project:
    run `docker ps` to see the projects
    run `docker exec -it typelevel-project-db-1 psql -U docker` to start the cointener

Once in the cointener you can:
    1. create database: create database demo;

    2. connect to the database: \c demo

    3. create table: create table students (id serial not null, name character varying not null, primary key(id));

    4. run queries: select * from students; 