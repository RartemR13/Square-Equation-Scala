val scala_version = "3.3.0"

ThisBuild / scalaVersion := "3.3.0"

ThisBuild / libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % Test
ThisBuild / libraryDependencies += "io.circe" %% "circe-yaml" % "0.14.2"

val circeVersion = "0.14.1"

ThisBuild / libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

ThisBuild / resolvers ++= Resolver.sonatypeOssRepos("snapshots")

val config_file = "config.yaml"

lazy val compileCopyTask = taskKey[Unit](s"Copy $config_file.")

compileCopyTask := {

  println(s"Start copying $config_file")
  val mainVersion:String = scala_version
  val to = target.value / ("scala-" + mainVersion) / "classes" / config_file
  val from = baseDirectory.value / config_file
  // IO.touch(to)
  IO.copyFile(from, to)
  println(s"$from  -> $to...done.")
}

compile in Compile := {
  compileCopyTask.value
  (compile in Compile).value
}