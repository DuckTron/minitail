# Build
docker build -t eureka-server .

# Run (foreground)
docker run --rm --name eureka-test eureka-server

# Run (detached)
docker run -d --name eureka-server eureka-server

# Docker Compose
docker compose up eureka-server

# Stop
docker stop eureka-server

# Remove container
docker rm -f eureka-server
