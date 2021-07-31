# Vit Server

Kotlin based server to manage all the User Authorization.

## Global prerequisites
You need to make sure that do you have available the following ports:
* 8080 Server port
* 5432 PostgreSql port (*you can map other port changing the docker-compose.yml file configuration in the postgreSQL service*).

To start setting up your local environment you need to have installed [docker](https://docs.docker.com/get-docker/) and [Docker compose](https://docs.docker.com/compose/) you need to choose the right configuration depend on your local Operating system.

## Local Setup
### Steps
1. Install and run the postgreSql docker image with `docker-compose up postgreSQL` command.
2. Build the application running gradle command `build`. 
3. Make sure you have defined the `local` profile setting the system property `-Dspring.profiles.active=local`.
4. Run the application service, it is available in `localhost:8080`.

## Dev Setup
### Steps
1. Install and run all the services in docker-compose.yml file with `docker-compose up` command.
2. Wait until the services are up.
3. Run the application service, it is available in `localhost:8080`.

### Updating the dev setup
If you need to update the current container you need to:
1. Build the application running gradle command `build`.
2. Force to rebuild you docker container with `docker-compose up -d --build` command.

