package kafka_spark_elasctisearch

import kafka_spark_elasctisearch.IndexInElasticsearch.start
import org.apache.log4j.{Level, Logger}
object Main {

  val rootLogger = Logger.getRootLogger()
  rootLogger.setLevel(Level.INFO)
  rootLogger.info("Starting the main process")

  def main(args: Array[String]): Unit = {


    start()
  }

}