package org.example.etl.load

import com.typesafe.config.Config
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, SaveMode}

object Loaders {
  private def loadHiveTable(dataFrame: DataFrame, tableName: String, partitionColumns: Seq[String], saveMode: SaveMode = SaveMode.Append): Unit = {
    dataFrame
      .write
      .format("hive")
      .mode(saveMode)
      .partitionBy(partitionColumns: _*)
      .saveAsTable(tableName)
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

  def writeEachBatchToHive(dataFrame: DataFrame, config: Config): Unit = {
    dataFrame
      .writeStream
      .format("console")
      .option("checkpointLocation", config.getString("kafka.checkpoint_location"))
      .trigger(Trigger.ProcessingTime(config.getString("kafka.micro_batch_interval")))
      .foreachBatch((batchDF: DataFrame, batchId: Long) => {
        println(s"Loading Data to Hive for Batch ID: ${batchId.toString}")
        batchDF.cache()
        batchDF.show(false)
        loadHiveTable(batchDF, config.getString("hive.table_name"), config.getString("hive.partition_columns").split(",").toSeq)
      })
      .start()
      .awaitTermination()
  }
}
