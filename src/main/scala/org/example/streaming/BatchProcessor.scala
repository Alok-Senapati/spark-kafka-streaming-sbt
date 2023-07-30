package org.example.streaming

import com.typesafe.config.Config
import org.apache.spark.sql.SparkSession
import org.example.etl.extract.Extractors.readKafkaStream
import org.example.etl.load.Loaders.writeEachBatchToHive
import org.example.etl.transform.Transformers.transformKafka
import org.example.schema.Schema.getKafkaSchema

class BatchProcessor(val spark: SparkSession, val config: Config) extends Processor {
  def run(): Unit = {
    val streamData = readKafkaStream(spark, config)
    val messageSchema = getKafkaSchema
    val parsedDF = transformKafka(streamData, messageSchema)
    writeEachBatchToHive(parsedDF, config)
  }
}
