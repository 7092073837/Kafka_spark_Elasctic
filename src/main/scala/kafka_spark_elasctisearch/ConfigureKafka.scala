package kafka_spark_elasctisearch
import kafka_spark_elasctisearch.Main.rootLogger
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import com.typesafe.config.ConfigFactory
object ConfigureKafka {

     rootLogger.info("Creating the spark session")

    val appConf = ConfigFactory.load()
    val spark = SparkSession
      .builder()
      .appName(appConf.getString("spark_application_name"))
      .master(appConf.getString("spark_master"))
      .getOrCreate()

  rootLogger.info("C spark session Created ")
  rootLogger.info("Connecting to kafka to read the streams")

  val kafka_source_dataframe = spark
        .readStream
        .format("kafka")
        .option("kafka.bootstrap.servers",appConf.getString("kafka_server_host"))
        .option("subscribe", appConf.getString("subscribe_topic"))
        .load()

}
