#!/bin/bash

echo "Starting Big Data Pipeline..."

# Step 1: Upload data to HDFS
echo "Step 1: Uploading CSV to HDFS..."
hdfs dfs -mkdir -p /user/data/sales
hdfs dfs -put sales_data.csv /user/data/sales/

# Step 2: Create Hive tables
echo "Step 2: Creating Hive tables..."
hive -f external_sales_table.hql
hive -f internal_revenue_table.hql

# Step 3: Build and run Spark job
echo "Step 3: Building and running Spark revenue calculation..."
mvn clean package
spark-submit \
  --class RevenueCalculator \
  --master yarn \
  --deploy-mode cluster \
  --num-executors 4 \
  --executor-cores 2 \
  --executor-memory 2g \
  --driver-memory 1g \
  target/revenue-calculator-1.0-SNAPSHOT.jar

# Step 4: Verify results
echo "Step 4: Verifying results..."
hive -f verify_results.hql

# Step 5: Export results
echo "Step 5: Exporting results to CSV..."
spark-submit \
  --class ExportToCSV \
  --master yarn \
  --deploy-mode cluster \
  --num-executors 2 \
  --executor-cores 1 \
  --executor-memory 1g \
  target/revenue-calculator-1.0-SNAPSHOT.jar

# Step 6: Verify export
echo "Step 6: Verifying export..."
hdfs dfs -ls /user/data/output/revenue_results_csv/

echo "Pipeline completed successfully!"