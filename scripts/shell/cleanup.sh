#!/bin/bash
# Cleanup script

echo "Cleaning up Big Data Pipeline resources..."

# Remove HDFS directories
hdfs dfs -rm -r /user/data/output
hdfs dfs -rm -r /user/data/sales

# Drop Hive tables
hive -e "DROP TABLE IF EXISTS sales_external;"
hive -e "DROP TABLE IF EXISTS product_revenue;"

# Remove Oozie workflows
hdfs dfs -rm -r /user/oozie/workflows/revenue-calculation

echo "Cleanup completed!"