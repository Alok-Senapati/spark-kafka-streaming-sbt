package org.example.etl.extract

import com.typesafe.config.Config
import org.apache.spark.sql.{DataFrame, SparkSession}

object Extractors {
  def readKafkaStream(spark: SparkSession, config: Config): DataFrame = {
    spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", config.getString("kafka.bootstrap_server"))
      .option("subscribe", config.getString("kafka.topic"))
      .option("startingOffsets", "earliest")
      .load()
  }

  def readKafkaBatch(spark: SparkSession, config: Config): DataFrame = {
    spark
      .read
      .format("kafka")
      .option("kafka.bootstrap.servers", config.getString("kafka.bootstrap_server"))
      .option("subscribe", config.getString("kafka.topic"))
      .option("kafka.group.id", config.getString("kafka.group_id"))
      .option("startingOffsets", "earliest")
      .option("endingOffsets", "latest")
      .load()
  }
}
