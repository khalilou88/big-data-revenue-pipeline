#!/bin/bash
# Deployment script for cluster

echo "Deploying Big Data Pipeline to cluster..."

# Build the project
mvn clean package

# Copy JAR to HDFS
hdfs dfs -put target/revenue-pipeline-1.0.0.jar /user/lib/

# Deploy Oozie workflow
hdfs dfs -put oozie/workflows/revenue-calculation/* /user/oozie/workflows/revenue-calculation/

# Deploy Hive scripts
hdfs dfs -put scripts/hive/* /user/scripts/hive/

echo "Deployment completed!"