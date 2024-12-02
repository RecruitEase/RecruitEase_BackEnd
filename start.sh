#!/bin/bash


cd Services

start_service() {
    local service_name=$1
    echo "Starting $service_name in dev mode..."
    (cd "$service_name" && mvn spring-boot:run) &
    

    if [ "$service_name" == "config-server" ] || [ "$service_name" == "discovery" ]; then
        sleep 15
    fi
}


trap "echo 'Stopping all services...'; kill 0" SIGINT SIGTERM EXIT


start_service "config-server"
start_service "discovery"
start_service "api-gateway"
#start_service "application-service"
start_service "auth-service"
#start_service "chat-service"
start_service "cv-service"
start_service "file-service"
start_service "interview-service"
start_service "job-service"
start_service "user-detail-service"
#start_service "recommendation"


wait
