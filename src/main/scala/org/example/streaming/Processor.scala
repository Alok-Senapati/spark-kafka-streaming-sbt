package org.example.streaming

import com.typesafe.config.Config
import org.apache.spark.sql.SparkSession

trait Processor {
  val spark: SparkSession
  val config: Config

  def run(): Unit
}
