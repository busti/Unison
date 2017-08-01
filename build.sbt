name := "Unison"
version := "1.0"

lazy val `unison` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

resolvers ++= Seq(
  "Scalaz Bintray Repo" at "https://dl.bintray.com/scalaz/releases",
  "Atlassian Releases"  at "https://maven.atlassian.com/public/",
  "Sonatype snapshots"  at "https://oss.sonatype.org/content/repositories/snapshots"
)

//TwirlKeys.constructorAnnotations += "@javax.inject.Inject()"

//Playframework default dependencies
libraryDependencies ++= Seq(
  ws,
  specs2 % Test,
  evolutions,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % Test
)

//Miscellaneous dependencies
libraryDependencies ++= Seq(
  "net.codingwell" %% "scala-guice" % "4.1.0",
  "com.iheart"     %% "ficus"       % "1.4.1"
)

//Database dependencies
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick"            % "3.0.0-M5",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0-M5",
  "com.h2database"    %  "h2"                    % "1.3.176"
)

//Security Dependencies
libraryDependencies ++= Seq(
  "com.mohiva" %% "play-silhouette"                 % "5.0.0-RC2",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "5.0.0-RC2",
  "com.mohiva" %% "play-silhouette-crypto-jca"      % "5.0.0-RC2",
  "com.mohiva" %% "play-silhouette-persistence"     % "5.0.0-RC2",
  "com.mohiva" %% "play-silhouette-testkit"         % "5.0.0-RC2" % "test"
)

//Webjars
libraryDependencies ++= Seq(
  "org.webjars"       %% "webjars-play"  % "2.6.0",
  "org.webjars.bower" % "bootstrap-sass" % "3.3.7"
)