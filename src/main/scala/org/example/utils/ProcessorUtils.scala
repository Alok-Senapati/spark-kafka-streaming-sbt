package org.example.utils

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession
import org.example.utils.SparkUtils.getSparkSession
import scala.reflect.runtime.universe._
import scala.reflect.runtime.{currentMirror, universe}
import org.example.streaming.Processor

object ProcessorUtils {
  // Define a Scala function to create an instance of the class identified by className and pass parameters to its constructor and return
  def getProcessorInstance(className: String, constructorParams: Array[AnyRef]): Processor = {
    val classSymbol = currentMirror.staticClass(className)
    val classMirror = currentMirror.reflectClass(classSymbol)
    val constructorSymbol = classSymbol.primaryConstructor.asMethod
    val constructorMirror = classMirror.reflectConstructor(constructorSymbol)
    constructorMirror(constructorParams: _*).asInstanceOf[Processor]
  }
}
