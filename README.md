# :dragon_face: Murabi

API for the Ur game acting as a Mur DB wrapper and game scheduler.

## :paw_prints: Dev steps

```bash
# Build all the artifacts
mvn clean package

# Build the local docker image
docker build . -t docker.pkg.github.com/messiah-of-ur/murabi/murabi:latest
```

## :running: Let's run it!

Murabi is stateless so running it should be easy. Here is an example of running it locally.

```bash
# Spin up a local postgresql
export DB_HOST=host.docker.internal
export DB_PORT=5432
export DB_USER=murabi
export DB_PASS=murabi
export DB_NAME=murabi

docker run --rm --name pq -e POSTGRES_PASSWORD=murabi -e POSTGRES_USER=murabi -e POSTGRES_PASSWORD=murabi -p 5432:5432 -d postgres

# Update the schema
psql -h localhost  -d murabi -U murabi -f schema/up.sql

# Since there is no presence locking mechanism implemented yet
# we have to pass in the murker addresses in the following format...
export MURKER_ADDRESSES="host.docker.internal:9000,host.docker.internal:9001"

# Start the server locally
docker run -e MURKER_ADDRESSES -e DB_HOST -e DB_PORT -e DB_USER -e DB_PASS -e DB_NAME -it --rm -p 8080:8080  docker.pkg.github.com/messiah-of-ur/murabi/murabi:latest
```

:warning: Note -> You'll also need some murkers to play through with it.
