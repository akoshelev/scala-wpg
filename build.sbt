name := "wallpaper-grabber"

version := "1.0.0"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val akkaVersion = "2.4.8"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-core" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaVersion,
    "ch.qos.logback" % "logback-classic" % "1.1.7"
  )
}

//scalacOptions ++= Seq("-feature",
//  "-language:higherKinds",
//  "-language:implicitConversions",
//  "-deprecation",
//  "-Ybackend:GenBCode",
//  "-Ydelambdafy:method",
//  "-target:jvm-1.8")


licenses +=("MIT", url("https://opensource.org/licenses/MIT"))

// enable updating file headers //
import de.heikoseeberger.sbtheader.license.MIT

// enable updating file headers
//
headers := Map(
  "scala" -> MIT("2016", "Alex Koshelev"),
  "conf" -> MIT("2016", "Alex Koshelev", "#")
)

//
// enable scala code formatting
import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform
//
// Scalariform settings
SbtScalariform.autoImport.scalariformPreferences := SbtScalariform.autoImport.scalariformPreferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(RewriteArrowSymbols, true)


// enable sbt-revolver
Revolver.settings ++ Seq(
  Revolver.enableDebugging(port = 5050, suspend = false),
  mainClass in reStart := Some("com.github.dnvriend.SimpleServer")
)

// enable plugins //
enablePlugins(AutomateHeaderPlugin)

