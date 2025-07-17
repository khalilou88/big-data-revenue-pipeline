// ExportToCSV.java
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class ExportToCSV {

    public static void main(String[] args) {
        // Initialize Spark Session
        SparkSession spark = SparkSession.builder()
                .appName("ExportToCSV")
                .enableHiveSupport()
                .getOrCreate();

        try {
            // Read results from Hive table
            Dataset<Row> df = spark.sql("SELECT * FROM product_revenue ORDER BY total_revenue DESC");

            // Show sample results
            System.out.println("Sample results to be exported:");
            df.show();

            // Export to CSV
            df.coalesce(1)
                    .write()
                    .mode("overwrite")
                    .option("header", "true")
                    .csv("/user/data/output/revenue_results_csv");

            System.out.println("Export to CSV completed successfully!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } finally {
            spark.stop();
        }
    }
}