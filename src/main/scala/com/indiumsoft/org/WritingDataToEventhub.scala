package com.indiumsoft.org


import org.apache.spark.eventhubs.{ConnectionStringBuilder, EventHubsConf}
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.elasticsearch.hadoop.cfg.ConfigurationOptions

object WritingDataToEventhub {
  def main(args: Array[String]): Unit = {

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
      .option("subscribe", "test")
      .load()

//    val dataSet: Dataset[( String ,String)] =df.selectExpr( "CAST(key AS STRING)","CAST(value AS STRING)")
//      .as[( String,String)]
//     val ds1 = dataSet.select($"value").alias("body")
val df1: DataFrame = df.selectExpr("CAST(value AS STRING)").as("body")

    val with1 = "Endpoint=sb://eventhubnp1.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=6HFRYfYqk93RGTseYUZjRx9onfG0MMG14kXL3LN9jI0="

    val connectionString = ConnectionStringBuilder(with1)   // defined in the previous code block
      .setEventHubName("strreaming")
      .build

     val ehConf = EventHubsConf(connectionString)

    val ds = df1
      .select("body.value")
      .writeStream
      .format("eventhubs")
      .options(ehConf.toMap)
      .option("checkpointLocation", "/tmp/")
      .trigger(Trigger.ProcessingTime("20 seconds"))
      .start()
    ds.awaitTermination()


  }


}
