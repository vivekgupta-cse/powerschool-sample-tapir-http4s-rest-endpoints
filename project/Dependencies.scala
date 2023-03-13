import sbt._

object Dependencies {
  object Versions {
    val http4s = "0.23.14"
    val circe = "0.14.1"
    val cats = "2.1.1"
    val catsEffect = "3.4.5"
    val caseInsensitive = "1.3.0"
    val fs2 = "2.5.6"
    val tapir = "1.2.9"
    val scopt = "4.1.0"
    val config = "1.3.1"
  }

  object http4s {
    val group = "org.http4s"

    val core = group %% "http4s-dsl" % Versions.http4s
    val blazeServer = group %% "http4s-blaze-server" % Versions.http4s
    val emberServer = group %% "http4s-ember-server" % Versions.http4s
    val circe = group %% "http4s-circe" % Versions.http4s
  }

  object circe {
    val group = "io.circe"
    val parser = group %% "circe-parser" % Versions.circe
    val generic = group %% "circe-generic" % Versions.circe
    val core = group %% "circe-core" % Versions.circe
  }

  object cats {
    val group = "org.typelevel"
    val core = group %% "cats-core" % Versions.cats
    val effect = group %% "cats-effect" % Versions.catsEffect
    val caseInsensitive = group %% "case-insensitive" % Versions.caseInsensitive
  }

  object fs2 {
    val group = "co.fs2"
    val core = group %% "fs2-core" % Versions.fs2
  }

  object tapir {
    val group = "com.softwaremill.sttp.tapir"
    val core = group %% "tapir-core" % Versions.tapir
    val jsonCirce = group %% "tapir-json-circe" % Versions.tapir
    val tapirServer = group %% "tapir-http4s-server" % Versions.tapir
    val swaggerUi = group %% "tapir-swagger-ui-bundle" % Versions.tapir
  }

  object scopt {
    val core = "com.github.scopt" %% "scopt" % Versions.scopt
  }

  object typesafe {
    val config = "com.typesafe" % "config" % Versions.config
  }
}
