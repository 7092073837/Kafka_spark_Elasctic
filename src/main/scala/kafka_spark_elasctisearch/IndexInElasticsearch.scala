package kafka_spark_elasctisearch
import org.apache.spark.sql.streaming.Trigger
import kafka_spark_elasctisearch.ConfigureKafka._
import kafka_spark_elasctisearch.Main.rootLogger
import main.scala.kafka_spark_elasctisearch.ProcessEvent.key_value_data

object IndexInElasticsearch {

  def start(): Unit = {

    rootLogger.info("Read data from spark streaming to elasticsearch")

    val query = key_value_data.writeStream
      .outputMode("append")
      .format("org.elasticsearch.spark.sql")
      .option("spark.es.index.auto.create", "true")
      .option("checkpointLocation", appConf.getString("checkpointLocation"))
      // .option("startingOffsets", "earliest")
      .option("es.resource", appConf.getString("ElasticSearch_Index_Name"))
      .option("es.nodes",  appConf.getString("ElasticSearch_Host_Name"))
      .trigger(Trigger.ProcessingTime("10 seconds"))
      .start()
    rootLogger.info("Records loaded in to elastic search")

    query.awaitTermination()

  }

}