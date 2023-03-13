package com.powerschool.apis.plainhttp4s

import cats.effect._
import fs2.Stream
import io.circe.Json
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.{HttpRoutes, Request}

object PersonApis {

  case class Person(firstName: String, lastName: String) {
    override def toString = s"$firstName $lastName"
  }

  /*
  val actor1 = Person("FNa1", "LNa1")
  val actor2 = Person("FNa2", "LNa2")
  val actor3 = Person("FNa3", "LNa3")
  val director1 = Person("FNd1", "LNd1")
  val director2 = Person("FNd2", "LNd2")
   */

  val collectionOfPersons = scala.collection.mutable.Set[Person]()


  def getPersonsList(req: Request[IO]) = {
    Stream.eval(Concurrent[IO].delay(
      Json.fromValues(collectionOfPersons.map(_.asJson))
    ))
  }

  def addPerson(req: Request[IO]): IO[Json] = {
    for (r <- req.as[String]) yield {
      val newPerson = decode[Person](r)
      newPerson match {
        case Left(failure) => Json.fromString("Invalid json")
        case Right(person) => {
          collectionOfPersons.add(person)
          Json.fromString("Successfully added")
        }
      }
    }
  }

  def personService = HttpRoutes.of[IO] {
    case req@GET -> Root / "v1" / "getPersons" => {
      Ok(getPersonsList(req))
    }

    case req@POST -> Root / "v1" / "addPerson" => {
      Ok(addPerson(req))
    }
  }
}
