package org.example.etl.load

import com.typesafe.config.Config
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, SaveMode}

object Loaders {
  def loadHiveTable(dataFrame: DataFrame, tableName: String, partitionColumns: Seq[String], saveMode: SaveMode): Unit = {
    dataFrame
      .write
      .mode(saveMode)
      .partitionBy(partitionColumns: _*)
      .saveAsTable(tableName)
  }

  def loadHiveTable(dataFrame: DataFrame, tableName: String, partitionColumns: Seq[String]): Unit = {
    loadHiveTable(dataFrame, tableName, partitionColumns, SaveMode.Append)
  }

  def loadHiveTable(dataFrame: DataFrame, tableName: String): Unit = {
    loadHiveTable(dataFrame, tableName, Seq.empty[String], SaveMode.Append)
  }

  def loadConsole(dataFrame: DataFrame, config: Config): Unit = {
    dataFrame
      .writeStream
      .format("console")
      .option("checkpointLocation", config.getString("kafka.checkpoint_location"))
      .trigger(Trigger.ProcessingTime(config.getString("kafka.micro_batch_interval")))
      .start()
      .awaitTermination()
  }
}
