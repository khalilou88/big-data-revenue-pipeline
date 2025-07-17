#!/bin/bash
# Environment setup script

echo "Setting up Big Data Pipeline environment..."

# Create HDFS directories
hdfs dfs -mkdir -p /user/data/sales
hdfs dfs -mkdir -p /user/data/output
hdfs dfs -mkdir -p /user/oozie/workflows

# Set permissions
hdfs dfs -chmod -R 755 /user/data
hdfs dfs -chmod -R 755 /user/oozie

# Create Hive database
hive -e "CREATE DATABASE IF NOT EXISTS revenue_db;"

echo "Environment setup completed!"