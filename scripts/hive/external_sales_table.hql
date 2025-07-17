-- external_sales_table.hql
CREATE EXTERNAL TABLE IF NOT EXISTS sales_external (
    product_id INT,
    product_name STRING,
    quantity INT,
    price DECIMAL(10,2),
    sale_date DATE
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION '/user/data/sales'
TBLPROPERTIES ("skip.header.line.count"="1");