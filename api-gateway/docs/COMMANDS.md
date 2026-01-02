# Commands

## Build
docker build -t api-gateway .

## Run (foreground)
docker run --rm --name api-gateway-test api-gateway

## Run (detached)
docker run -d --name api-gateway api-gateway

## Docker Compose
docker compose up api-gateway

## Stop
docker stop api-gateway

## Remove container
docker rm -f api-gateway
