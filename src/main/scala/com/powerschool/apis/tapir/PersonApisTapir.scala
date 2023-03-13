package com.powerschool.apis.tapir

import cats.effect.IO
import io.circe.generic.auto._
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._
import sttp.tapir.server.ServerEndpoint

object PersonApisTapir {

  case class Person(firstName: String, lastName: String) {
    override def toString = s"$firstName $lastName"
  }

  val collectionOfPersons = scala.collection.mutable.Set[Person]()

  val personListing: PublicEndpoint[Unit, String, List[Person], Any] = endpoint.get
    .in("v1" / "getPersons")
    .errorOut(stringBody.description("Something went wrong"))
    .out(jsonBody[List[Person]])
  val personListingServerEndpoint: ServerEndpoint[Any, IO] = personListing.serverLogicSuccess(_ => IO.pure(collectionOfPersons.toList))

  val addPersonInList: PublicEndpoint[Person, String, String, Any] = endpoint.post
    .in("v1" / "addPerson")
    .in(jsonBody[Person])
    .errorOut(stringBody.description("Something went wrong"))
    .out(jsonBody[String])


  val addPersonEndpoint = addPersonInList.serverLogicSuccess(p => {
    p match {
      case Person(f, l) => collectionOfPersons.add(Person(f, l)); IO.pure("Success")
      case _ => IO.pure("Failure")
    }
  })

  val personApiEndpoints: List[ServerEndpoint[Any, IO]] = List(personListingServerEndpoint, addPersonEndpoint)
}
