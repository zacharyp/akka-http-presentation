##Akka Streams
###An active process that involves moving and transforming data.
- Asynchronous -> threads are free when not in use
- Bounded -> will not use all the memory for message buffer
- Controlled by demand




![streams](images/waterfall.jpg)
### Note: not free flowing



##Akka Stream elements
- Source -> Singular output that emits data elements when downstream stages are ready to process
- Flow -> Transforms data, and connects upstream and downstream elements (note: can include graphs)
- Sink -> Singular input that accepts upstream elements




##Akka Stream example
```tut:silent
import scala.concurrent.duration._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor._, akka.stream._, akka.stream.scaladsl._

implicit val actorSystem = ActorSystem("stream-example")
implicit val actorMaterializer = ActorMaterializer()

val source: Source[Int,akka.NotUsed] = Source(0 to 9)
val flow: Flow[Int,Int,akka.NotUsed] = Flow[Int].map(i => i + 1)
val sink: Sink[Int,Future[Int]] = Sink.fold[Int, Int](0)(_ + _)

val result = source.via(flow).runWith(sink)

val finalValue = Await.result(result, 1.seconds) // 55

```

