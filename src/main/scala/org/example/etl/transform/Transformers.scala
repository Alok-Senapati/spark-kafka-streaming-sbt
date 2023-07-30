package org.example.etl.transform

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, current_timestamp, date_format, from_json}
import org.apache.spark.sql.types.{StringType, StructType}

object Transformers {
  def transformKafka(streamData: DataFrame, messageSchema: StructType): DataFrame = {
    streamData
      .select(
        col("key").cast(StringType).as("messageOffset"),
        from_json(col("value").cast(StringType), messageSchema).as("data")
      )
      .select(
        col("messageOffset"),
        col("data.*")
      )
      .withColumn("processingTimestamp", date_format(current_timestamp(), "yyyy-MM-dd-HH-mm"))
  }
}
