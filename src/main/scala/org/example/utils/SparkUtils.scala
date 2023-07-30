package org.example.utils

import org.apache.spark.SparkConf
import com.typesafe.config.Config
import org.apache.spark.sql.SparkSession

import scala.collection.convert.ImplicitConversions.`collection AsScalaIterable`

object SparkUtils {
  def getSparkConf(config: Config): SparkConf = {
    val sparkConf = new SparkConf()
    config.entrySet()
      .filter(entry => entry.getKey.startsWith("spark"))
      .foreach(entry => {
        sparkConf.set(entry.getKey, entry.getValue.unwrapped().toString
      )
    })
    sparkConf
  }

  def getSparkSession(config: Config): SparkSession = {
    val sparkConf = getSparkConf(config)
    val spark = SparkSession.builder()
      .config(sparkConf)
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .enableHiveSupport()
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    spark
  }
}
