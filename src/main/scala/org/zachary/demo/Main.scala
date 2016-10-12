package org.zachary.demo

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.io.IO
import akka.stream.ActorMaterializer
import spray.can.{Http => SprayHttp}

object Main extends App {
  private val main: Main = new Main

  main.start()
}

class Main {

  lazy implicit val actorSystem = ActorSystem("akka-http-demo")
  lazy implicit val actorMaterializer = ActorMaterializer()

  def start() = {

    val service = actorSystem.actorOf(Props(classOf[SprayEndpoints], actorSystem, actorMaterializer))
    IO(SprayHttp)(actorSystem) ! SprayHttp.Bind(service, "0.0.0.0", port = 8080)

    val akkaHttpDemo = new AkkaHttpDemo
    val bindingFuture = Http().bindAndHandle(akkaHttpDemo.route, "localhost", 8090)

  }
}
