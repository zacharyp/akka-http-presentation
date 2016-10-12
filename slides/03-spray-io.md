##Spray.IO
###High performance HTTP/REST with Akka Actors




##Spray.IO features
- Immutable case class based HTTP model
- HTTP client and server
- DSL for REST endpoints
- Asynchronous, non-blocking, actor based




##Spray.IO drawbacks
- Powerful DSL can be confusing
- Implicit argument chains can lead to horrific stack traces
- No websocket support
- Handling chunked requests not easy




##Spray.IO REST example
```scala
import spray.routing.SimpleRoutingApp
import akka.actor._

object Main extends App with SimpleRoutingApp {
  implicit val system = ActorSystem("my-system")

  startServer(interface = "localhost", port = 8080) {
    path("hello") {
      get {
        complete {
          <h1>Say hello to spray</h1>
        }
      }
    }
  }
}
```
