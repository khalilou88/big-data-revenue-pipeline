// RevenueCalculator.java
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;

public class RevenueCalculator {

    public static void main(String[] args) {
        // Initialize Spark Session
        SparkSession spark = SparkSession.builder()
                .appName("ProductRevenueCalculator")
                .enableHiveSupport()
                .getOrCreate();

        try {
            // Read from Hive external table
            Dataset<Row> df = spark.sql("SELECT * FROM sales_external");

            // Show sample data
            System.out.println("Sample data from sales_external:");
            df.show(10);

            // Calculate revenue per row
            Dataset<Row> dfWithRevenue = df.withColumn("revenue",
                    functions.col("quantity").cast(DataTypes.DoubleType)
                            .multiply(functions.col("price").cast(DataTypes.DoubleType)));

            // Calculate total revenue per product
            Dataset<Row> revenueByProduct = dfWithRevenue
                    .groupBy("product_id", "product_name")
                    .agg(functions.sum("revenue").alias("total_revenue"));

            // Show results
            System.out.println("Revenue calculation results:");
            revenueByProduct.show();

            // Write results to Hive table
            revenueByProduct.write()
                    .mode("overwrite")
                    .saveAsTable("product_revenue");

            System.out.println("Revenue calculation completed successfully!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } finally {
            spark.stop();
        }
    }
}