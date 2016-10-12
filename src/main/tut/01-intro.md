
## Old JVM ways: InputStreams and OutputStreams
###Synchronous
###Blocking
###Bounded by Threads



##100/s producer  --------->  QUEUE --------->  10/s consumer

###Queue will fill up, memory will be exhausted




##Back Pressure to the Rescue!




##Back Pressure
-A means of flow-control, a way for consumers of data to notify a producer about their current availability, effectively slowing down the upstream producer to match their consumption speeds.
#####(Note: TCP implements back pressure by design)



##Dynamic push-pull
- Asynchronous non-blocking data flow
- Asynchronous non-blocking demand flow

![backpress](images/publisher-demand.jpg)
####"push"-like behavior when consumer is faster
####"pull"-like behavior when subscriber is faster


