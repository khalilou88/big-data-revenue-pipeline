-- internal_revenue_table.hql
CREATE TABLE IF NOT EXISTS product_revenue (
    product_id INT,
    product_name STRING,
    total_revenue DECIMAL(15,2)
)
STORED AS PARQUET;