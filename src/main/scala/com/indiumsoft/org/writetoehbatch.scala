package com.indiumsoft.org

import org.apache.spark.sql.functions.{concat, lit}
import org.apache.spark.eventhubs._
import org.apache.spark.sql.functions._
import org.apache.spark.eventhubs.{ConnectionStringBuilder, EventHubsConf}
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.elasticsearch.hadoop.cfg.ConfigurationOptions

object writetoehbatch {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(" Spark Kafka basic example")
      .master("local")
      .getOrCreate()
    import spark.implicits._

    val with1 = "Endpoint=sb://veeraeventhubs.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=taQL1jwG+8YcUTZF6l91h046f3QTc1b4jvy/9vYYbik="

    val connectionString = ConnectionStringBuilder(with1)   // defined in the previous code block
      .setEventHubName("veeraeventh")
      .build

    val ehConf = EventHubsConf(connectionString)


    val df = spark.read
      .format("csv")
      .option("header", "true") //first line in file has headers
      //.option("mode", "DROPMALFORMED")
     // .load("/home/indium/Downloads/price.json").as("body")
      .load("/home/indium/Downloads/Downloads/1500000 Sales Records.csv")



    df.createOrReplaceTempView("sales")
    val df1 = spark.sql("select concat('\"Id\":1,\"Region\":\"Sub-Saharan Africa\",\"Country\":\"Chad\",\"Item Type\":\"Office Supplies\",\"Sales Channel\":\"Online\",\"Order Priority\":\"L\",\"Order Date\":\"1/27/2011\",\"Order ID\":292494523,\"Ship Date\":\"2/12/2011\",\"Units Sold\":4484,\"Unit Price\":651.21,\"Unit Cost\":524.96,\"Total Revenue\":2920025.64,\"Total Cost\":2353920.64,\"Total Profit\":566105') from sales ").toDF("body")




//    val bodyColumn = concat(lit("random nunmber: "), rand(),lit("even or odd")).as("body")
//
//    val df = spark.range(200).select(bodyColumn)
 val ds = df1.select("body")
      .write
      .format("eventhubs")
      .options(ehConf.toMap)
      .save()

  }

}
