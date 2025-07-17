// AdvancedExportToCSV.java
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;

public class AdvancedExportToCSV {

    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder()
                .appName("AdvancedExportToCSV")
                .enableHiveSupport()
                .getOrCreate();

        try {
            // Read results from Hive table
            Dataset<Row> df = spark.sql("SELECT * FROM product_revenue ORDER BY total_revenue DESC");

            // Add additional columns and formatting
            Dataset<Row> formattedDf = df
                    .withColumn("formatted_revenue",
                            functions.format_number(functions.col("total_revenue"), 2))
                    .withColumn("export_date",
                            functions.current_date())
                    .withColumn("rank",
                            functions.row_number().over(
                                    org.apache.spark.sql.expressions.Window.orderBy(
                                            functions.col("total_revenue").desc())));

            // Select final columns for export
            Dataset<Row> finalDf = formattedDf.select(
                    functions.col("rank"),
                    functions.col("product_id"),
                    functions.col("product_name"),
                    functions.col("total_revenue"),
                    functions.col("formatted_revenue"),
                    functions.col("export_date")
            );

            // Show formatted results
            System.out.println("Formatted results to be exported:");
            finalDf.show();

            // Export to CSV with custom path based on current date
            String outputPath = "/user/data/output/revenue_results_" +
                    java.time.LocalDate.now().toString().replace("-", "");

            finalDf.coalesce(1)
                    .write()
                    .mode("overwrite")
                    .option("header", "true")
                    .csv(outputPath);

            System.out.println("Advanced export to CSV completed successfully!");
            System.out.println("Output path: " + outputPath);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } finally {
            spark.stop();
        }
    }
}