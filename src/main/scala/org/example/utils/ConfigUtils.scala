package org.example.utils

import com.typesafe.config.ConfigFactory
import com.typesafe.config.Config

object ConfigUtils {
  def getConfig(env: String): Config = {
    ConfigFactory.load("stream_console_writer.conf").getConfig(env)
  }

  def main(args: Array[String]): Unit = {
    val config = ConfigUtils.getConfig("stream_console_writer.conf")
    val devConfig = config.getString("dev.kafka.bootstrap_server")
    println(devConfig)
    println(config.getConfig("dev").entrySet().forEach(println))
  }
}
