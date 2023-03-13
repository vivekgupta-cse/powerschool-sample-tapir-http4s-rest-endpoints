package com.powerschool

import cats.effect._
import com.powerschool.apis.plainhttp4s.MovieApis._
import com.powerschool.apis.plainhttp4s.PersonApis._
import com.typesafe.config.{Config, ConfigFactory}
import org.http4s.implicits._
import org.http4s.server._
import org.http4s.server.blaze.BlazeServerBuilder

object ApiServerPlainHttp4s extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val allRoutes = Router[IO](mappings =
      "/" -> personService,
      "/" -> movieService
    ).orNotFound

    val applicationConf: Config = ConfigFactory.load("app.conf")

    BlazeServerBuilder[IO]
      .bindHttp(applicationConf.getInt("serverPort"), applicationConf.getString("serverUrl"))
      .withHttpApp(allRoutes)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}
