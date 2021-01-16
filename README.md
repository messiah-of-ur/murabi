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

Murabi is stateless so running it should be easy.

```bash
# Since there is no presence locking mechanism implemented yet
# we have to pass in the murker addresses in the following format...
export MURKER_ADDRESSES="host.docker.internal:9000,host.docker.internal:9001"

# Start the server locally
docker run -e MURKER_ADDRESSES -it --rm -p 8080:8080  docker.pkg.github.com/messiah-of-ur/murabi/murabi:latest
```
