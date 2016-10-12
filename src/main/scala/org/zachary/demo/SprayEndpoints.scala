package org.zachary.demo

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.json._
import spray.routing.{HttpServiceActor, Route}

class SprayEndpoints(actorSystem: ActorSystem, am: ActorMaterializer) extends HttpServiceActor {
  implicit val printer = CompactPrinter
  implicit val orderFormat = jsonFormat2(Order.apply)

  implicit val actorMaterializer = am
  implicit val ec = actorSystem.dispatcher

  def receive = runRoute(routes)

  lazy val routes: Route = blockingGet

  val blockingGet: Route = path("blocking" / IntNumber)(i => {
    get {
      complete {
        Order.source(i)
          .runFold(Seq.empty[Order])(_ :+ _)
      }
    }
  })

}
