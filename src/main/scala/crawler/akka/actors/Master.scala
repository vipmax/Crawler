package crawler.akka.actors

import akka.actor.{Actor, ActorLogging}
import crawler.akka.messages.{TaskInfo, WorkersEvents}
import crawler.twitter.TwitterTest

/**
  * Created by Max on 24.10.2015.
  */
class Master extends Actor with ActorLogging {
  private val credentials = TwitterTest.getCredentials()

   override def receive: Receive = {
     case WorkersEvents.WorkerUp() =>
       log.warning(s"Have got Hi from $sender")
       sender ! TaskInfo(credentials(0),List("ISIS"))


   }

 }
