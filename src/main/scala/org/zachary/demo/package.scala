package org.zachary

import akka.NotUsed
import akka.stream.scaladsl.Source
import akka.stream.{OverflowStrategy, ThrottleMode}

import scala.concurrent.duration._
import scala.util.Random

package object demo {

  case class Order(id: Int, name: String)

  object Order {

    def source(count: Int): Source[Order, NotUsed] = {
      Source.fromIterator(() => Iterator.range(0, count))
//        .buffer(10, OverflowStrategy.backpressure)
        .throttle(100, 1.seconds, 10, ThrottleMode.Shaping)
        .map(i => Order(i, Random.alphanumeric.take(10).mkString))
    }
  }

}