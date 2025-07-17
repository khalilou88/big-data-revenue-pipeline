-- export_to_csv.hql
INSERT OVERWRITE DIRECTORY '/user/data/output/revenue_results'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
SELECT
    CONCAT('product_id,product_name,total_revenue') as header
FROM product_revenue
LIMIT 1

UNION ALL

SELECT
    CONCAT_WS(',',
        CAST(product_id AS STRING),
        product_name,
        CAST(total_revenue AS STRING)
    ) as row_data
FROM product_revenue
ORDER BY total_revenue DESC;