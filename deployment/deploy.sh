#!/bin/bash

SERVER_IP="149.28.156.237"
SERVER_DIR="/app/services"
SERVICE_NAME="story-spider-service"
VERSION="0.0.1-SNAPSHOT"

echo "start deployment to server $SERVER_IP"

echo "building jar file..."
../gradlew clean build -p ../

echo "uploading jar file..."
scp ../docker/Dockerfile root@$SERVER_IP:$SERVER_DIR/Dockerfile
scp ../docker/docker-compose.yml root@$SERVER_IP:$SERVER_DIR/docker-compose.yml
scp ../build/libs/$SERVICE_NAME-$VERSION.jar root@$SERVER_IP:$SERVER_DIR/target/app.jar

echo "restarting app on server..."
ssh root@$SERVER_IP "cd $SERVER_DIR && docker compose up -d --build"

echo "completed!"
