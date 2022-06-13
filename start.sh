#!/bin/bash
cd docker
docker-compose -f docker-compose.yml up -d cassandra prometheus node-exporter cadence cadence-web grafana postgres
echo "sleep for 15s to be sure that cadence server has started in order to register domain"
sleep 15s
docker run --network=host --rm ubercadence/cli:master --do weather-domain domain register -rd 1
docker-compose -f docker-compose.yml up -d --build activity-worker workflow-worker workflow-launcher
