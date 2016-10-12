package org.zachary.demo

import akka.actor.ActorSystem
import akka.http.scaladsl.common.{EntityStreamingSupport, JsonEntityStreamingSupport}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.Credentials
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import akka.util.ByteString
import spray.json.{CompactPrinter, DefaultJsonProtocol}

import scala.concurrent.Future

object AkkaHttpDemo {

  object OrderProtocol extends SprayJsonSupport with DefaultJsonProtocol {
    implicit val printer = CompactPrinter
    implicit val orderFormat = jsonFormat2(Order.apply)
  }

}

class AkkaHttpDemo(implicit actorSystem: ActorSystem, am: ActorMaterializer) {

  import AkkaHttpDemo.OrderProtocol._

  val start = ByteString("[")
  val sep = ByteString(",")
  val end = ByteString("]")

  implicit val jsonStreamingSupport: JsonEntityStreamingSupport = EntityStreamingSupport.json()
    .withFramingRenderer(Flow[ByteString].intersperse(start, sep, end))

  val streaming: Route = path("streaming" / IntNumber)(i => {
    get {
      complete {
        Order.source(i)
      }
    }
  })

  def getOrdersFromDB: Future[Order] = Future.successful(Order(1, "foo"))

  def myUserPassAuthenticator(credentials: Credentials): Option[String] =
    credentials match {
      case p @ Credentials.Provided(id) if p.verify("p4ssw0rd") => Some(id)
      case _ => None
    }

  val ordersExample: Route = {
    path("orders") {
      authenticateBasic(realm = "admin area", myUserPassAuthenticator) { user =>
        get {
//          encodeResponseWith(Deflate) {
            complete {
              // marshal custom object with in-scope marshaller
              getOrdersFromDB
            }
//          }
        } ~ post {
          // decompress gzipped or deflated requests if required
          decodeRequest {
            // unmarshal with in-scope unmarshaller
            entity(as[Order]) { order =>
              // transfer to newly spawned actor
              //                detach() {
              complete {
                // ... write order to DB
                s"Order ${order.id} received named ${order.name}"
              }
            }
          }
        }
      }
    }
  }

  val route = streaming ~ ordersExample
}
