package org.example

import com.typesafe.config.ConfigFactory
import org.example.utils.ProcessorUtils.getProcessorInstance
import org.example.utils.SparkUtils.getSparkSession

object JobRunner {
  def main(args: Array[String]): Unit = {
    val jobName = args(0)
    val envName = args(1)

    val rootConfig = ConfigFactory.load(s"$jobName.conf")
    val processorClass = rootConfig.getString("processor_class")
    val jobConfig = rootConfig.getConfig(envName)
    val spark = getSparkSession(jobConfig)

    val processor = getProcessorInstance(processorClass, Array(spark, jobConfig))
    processor.run()
  }

}
