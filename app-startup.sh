#!/bin/bash
echo "Running app-startup.sh ... "
mvn clean package
mvn spring-boot:run