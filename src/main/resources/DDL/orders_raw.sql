CREATE TABLE IF NOT EXISTS orders_raw (
    messageOffset string,
    orderID string,
    custID string,
    channel string,
    storeID string,
    totalPrice double,
    couponID string,
    totalPayment double,
    storeName string,
    firstName string,
    lastName string,
    age int,
    gender string,
    addrID string,
    email string,
    lpoints int
) PARTITIONED BY (
    processingTimestamp string
) STORED AS PARQUET;