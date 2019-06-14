package com.indiumsoft.org

import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.streaming.{StreamingQuery, Trigger}
import com.typesafe.config.ConfigFactory
import org.elasticsearch.hadoop.cfg.ConfigurationOptions

object kafkatosparkstreeming {
  def main(args: Array[String]): Unit = {

    // val conf = ConfigFactory.load.getConfig(args())
    val spark = SparkSession
      .builder()
      .appName(" Spark Kafka basic example")
      .master("local")
      .config(ConfigurationOptions.ES_NODES, "localhost")
      .config(ConfigurationOptions.ES_PORT, "9200")
      .getOrCreate()

   import spark.implicits._

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers","localhost:9092")
      .option("subscribe", "test2")
      .load()
 // df.printSchema()
      val dataSet: Dataset[( String ,String)] =df.selectExpr( "CAST(key AS STRING)","CAST(value AS STRING)")
     .as[( String,String)]
       val ds1 = dataSet.toJSON
// dataSet.printSchema()

 val query = dataSet.writeStream
   .outputMode("append")
     .format("org.elasticsearch.spark.sql")
  .option("spark.es.index.auto.create", "true")
   .option("checkpointLocation", "/tmp/test2_data")
  // .option("startingOffsets", "earliest")
  .option("es.resource", "test2/indium")
    .option("es.nodes", "localhost")
    .trigger(Trigger.ProcessingTime("10 seconds"))
      .start()
       query.awaitTermination()

  }

}
