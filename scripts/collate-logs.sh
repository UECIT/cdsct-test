#!/usr/bin/env bash

mkdir -p target/logs
cd target/logs
docker-compose logs -t ems            > ems-logs.txt
docker-compose logs -t cdss           > cdss-logs.txt
docker-compose logs -t dos            > dos-logs.txt
docker-compose logs -t fhir-server    > fhir-server-logs.txt
docker-compose logs -t elasticsearch  > elasticsearch-logs.txt