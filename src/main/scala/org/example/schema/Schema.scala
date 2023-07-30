package org.example.schema

import org.apache.spark.sql.types.{DateType, DoubleType, IntegerType, StringType, StructField, StructType}

object Schema {
  def getKafkaSchema: StructType = {
    StructType(
      Array(
        StructField("orderID", StringType, nullable = false),
        StructField("custID", StringType, nullable = false),
        StructField("channel", StringType, nullable = false),
        StructField("storeID", StringType, nullable = true),
        StructField("totalPrice", DoubleType, nullable = false),
        StructField("couponID", StringType, nullable = true),
        StructField("totalPayment", DoubleType, nullable = false),
        StructField("storeName", StringType, nullable = true),
        StructField("firstName", StringType, nullable = false),
        StructField("lastName", StringType, nullable = false),
        StructField("age", IntegerType, nullable = false),
        StructField("gender", StringType, nullable = false),
        StructField("addrID", StringType, nullable = false),
        StructField("email", StringType, nullable = false),
        StructField("lpoints", IntegerType, nullable = false),
      )
    )
  }

}
