package com.indiumsoft.org

import org.apache.spark.sql.SparkSession
import org.elasticsearch.hadoop.cfg.ConfigurationOptions
import org.elasticsearch.spark.sql._

case class AlbumIndex(artist: String, yearOfRelease: Int, albumName: String)

object sparktoES {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("WriteToES")
      .master("local[*]")
      //.config("spark.es.nodes","http://192.168.12.3")
      .config(ConfigurationOptions.ES_NODES, "http://192.168.12.3")
      .config(ConfigurationOptions.ES_PORT, "9200")
     // .config("spark.es.port","9200")
      //.config("spark.es.nodes.wan.only","true") //Needed for ES on AWS
      .getOrCreate()

    import spark.implicits._

    val indexDocuments = Seq(
      AlbumIndex("Led Zeppelin",1969,"Led Zeppelin"),
      AlbumIndex("Boston",1976,"Boston"),
      AlbumIndex("Fleetwood Mac", 1979,"Tusk")
    ).toDF
    val ds1 = indexDocuments.toJSON
   // indexDocuments.show()
    ds1.show()
  ds1.saveToEs("demo/albumindex")
  }
}




