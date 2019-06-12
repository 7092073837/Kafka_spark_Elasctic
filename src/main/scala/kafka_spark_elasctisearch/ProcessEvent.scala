package main.scala.kafka_spark_elasctisearch

import kafka_spark_elasctisearch.Main.rootLogger
import kafka_spark_elasctisearch.ConfigureKafka
import org.apache.spark.sql.Dataset

object ProcessEvent {

  import kafka_spark_elasctisearch.ConfigureKafka.spark.implicits._
  rootLogger.info("Reading the kafka streams key value to the spark dataframe")

  val key_value_data: Dataset[(String, String)] = ConfigureKafka.kafka_source_dataframe.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
    .as[(String, String)]
 // val ds1 = dataSet.toJSON


}
