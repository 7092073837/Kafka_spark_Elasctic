name := "KafkatoElasctic"

version := "0.1"

scalaVersion := "2.11.8"

// https://mvnrepository.com/artifact/com.typesafe/config
libraryDependencies += "com.typesafe" % "config" % "1.3.4"


// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.2"

// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.2"

// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.4.2" % "provided"

// https://mvnrepository.com/artifact/org.apache.spark/spark-hive
libraryDependencies += "org.apache.spark" %% "spark-hive" % "2.4.2" % "provided"

// libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka" % "2.4.2"
libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.4.2"

libraryDependencies += "org.elasticsearch" %% "elasticsearch-spark-20" % "6.7.2"

// https://mvnrepository.com/artifact/com.microsoft.azure/azure-eventhubs-spark
libraryDependencies += "com.microsoft.azure" %% "azure-eventhubs-spark" % "2.3.12"
