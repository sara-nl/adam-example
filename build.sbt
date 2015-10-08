version := "0.1"

organization := "nl.surfsara.spark"

scalaVersion := "2.10.4"

name := "adam_example"

val sparkVersion  = "1.4.1"

libraryDependencies ++= Seq(
  "org.apache.spark"  %% "spark-core"    % sparkVersion  % "provided",
  "org.bdgenomics.adam" % "adam-core_2.10" % "0.17.1"   
)

libraryDependencies += "org.apache.spark" % "spark-mllib_2.10" % "1.4.1"

libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.6.0" % "provided"

libraryDependencies += "org.bdgenomics.bdg-utils" % "bdg-utils-misc" % "0.1.2"

libraryDependencies += "org.bdgenomics.bdg-formats" % "bdg-formats" % "0.6.1"

libraryDependencies += "org.bdgenomics.utils" % "utils-misc_2.10" % "0.2.3"

libraryDependencies += "org.bdgenomics.utils" % "utils-io_2.10" % "0.2.3"

libraryDependencies += "org.bdgenomics.utils" % "utils-cli_2.10" % "0.2.3"

libraryDependencies += "org.bdgenomics.utils" % "utils-metrics_2.10" % "0.2.3"

libraryDependencies += "org.bdgenomics.adam" % "adam-apis_2.10" % "0.17.1"

libraryDependencies += "org.bdgenomics.utils" % "utils-serialization_2.10" % "0.2.3"

libraryDependencies += "org.bdgenomics.bdg-utils" % "bdg-utils-parquet" % "0.1.1"

mergeStrategy in assembly <<= (mergeStrategy in assembly) ((old) => {
  case x if Assembly.isConfigFile(x) =>
    MergeStrategy.concat
  case PathList(ps @ _*) if Assembly.isReadme(ps.last) || Assembly.isLicenseFile(ps.last) =>
    MergeStrategy.rename
  case PathList("META-INF", xs @ _*) =>
    (xs map {_.toLowerCase}) match {
      case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
        MergeStrategy.discard
      case ps @ (x :: xs) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
        MergeStrategy.discard
      case "plexus" :: xs =>
        MergeStrategy.discard
      case "services" :: xs =>
        MergeStrategy.filterDistinctLines
      case ("spring.schemas" :: Nil) | ("spring.handlers" :: Nil) =>
        MergeStrategy.filterDistinctLines
      case _ => MergeStrategy.first 
    }
  case PathList(_*) => MergeStrategy.first
})