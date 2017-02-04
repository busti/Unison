name := "Unison"
version := "1.0"

lazy val `unison` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += "Atlassian Releases" at "https://maven.atlassian.com/public/"

/***************
* Dependencies *
***************/

//Playframework default dependencies
libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  evolutions
)

//Database dependencies
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick"            % "2.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.2",
  "com.h2database"    %  "h2"                    % "1.3.176"
)

libraryDependencies ++= Seq(
  "com.mohiva" %% "play-silhouette"                 % "4.0.0",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "4.0.0",
  "com.mohiva" %% "play-silhouette-crypto-jca"      % "4.0.0",
  "com.mohiva" %% "play-silhouette-persistence"     % "4.0.0",
  "com.mohiva" %% "play-silhouette-testkit"         % "4.0.0" % "test"
)

//Webjars
libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.5.0",
  "org.webjars" %  "bootstrap"    % "3.3.4",
  "org.webjars" %  "jquery"       % "3.1.1-1"
)