package org.zachary.demo

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Source}
import akka.stream.{OverflowStrategy, ThrottleMode}

import scala.concurrent.duration._
import scala.util.Random

object OrderSourceFlow {

  def orderSource(count: Int): Source[Int, NotUsed] = Source.fromIterator(() => Iterator.range(0, count))

  def flowOfOrders(): Flow[Int, Order, NotUsed] = {
    Flow[Int]
      .buffer(10, OverflowStrategy.backpressure)
      .throttle(100, 1.seconds, 10, ThrottleMode.Shaping)
      .map(i => Order(i, Random.alphanumeric.take(10).mkString))
  }
}
