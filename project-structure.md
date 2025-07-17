## Project Structure

```
big-data-revenue-pipeline/
├── README.md
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   ├── RevenueCalculator.java
│       │   ├── ExportToCSV.java
│       │   └── AdvancedExportToCSV.java
│       └── resources/
│           ├── log4j.properties
│           └── application.properties
│    └── test/
│        └── java/
│            ├── RevenueCalculatorTest.java
│            └── ExportToCSVTest.java
├── scripts/
│   ├── hive/
│   │   ├── external_sales_table.hql
│   │   ├── internal_revenue_table.hql
│   │   ├── verify_results.hql
│   │   └── export_to_csv.hql
│   ├── shell/
│   │   ├── run_pipeline.sh
│   │   ├── setup_environment.sh
│   │   ├── deploy_to_cluster.sh
│   │   └── cleanup.sh
│   └── data/
│       └── sample_sales_data.csv
├── oozie/
│   ├── workflows/
│   │   ├── revenue-calculation/
│   │   │   ├── workflow.xml
│   │   │   └── job.properties
│   │   └── coordinator/
│   │       ├── coordinator.xml
│   │       └── coordinator.properties
│   └── lib/
│       └── (JAR files will be placed here)
├── config/
│   ├── cluster/
│   │   ├── hadoop-config.xml
│   │   ├── hive-site.xml
│   │   └── spark-defaults.conf
│   └── application/
│       ├── development.properties
│       ├── production.properties
│       └── test.properties
├── docs/
│   ├── setup-guide.md
│   ├── deployment-guide.md
│   ├── troubleshooting.md
│   └── architecture-diagram.png
└── target/
    └── (Maven build artifacts)
```
