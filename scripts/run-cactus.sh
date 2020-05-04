#!/usr/bin/env bash

healthcheck() {
    until [[ $(curl -s -o /dev/null -w %{http_code} $1) == "200" ]] ; do
        echo "healthcheck: $1 attempt $attempt_counter"
        if [ ${attempt_counter} -eq ${max_attempts} ];then
        echo "Max attempts reached"
        exit 1
        fi
        attempt_counter=$(($attempt_counter+1))
        sleep $retry_wait
    done
    echo "$1 ready"
}

echo "Planting a cactus..."

docker-compose up -d

attempt_counter=1
retry_wait=5
max_attempts=18 #90 seconds

echo "waiting for cdss..."
healthcheck "http://localhost:8080/fhir/metadata"
echo "waiting for fhir server..."
healthcheck "http://localhost:8084/fhir/metadata"
echo "waiting for ems..."
healthcheck "http://localhost:8083/fhir/metadata"
echo "waiting for dos..."
healthcheck "http://localhost:8085/fhir/metadata"