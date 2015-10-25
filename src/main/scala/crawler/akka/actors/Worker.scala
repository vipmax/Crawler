package crawler.akka.actors

import akka.actor.{Actor, ActorLogging}
import crawler.akka.messages.WorkersEvents.WorkerUp
import crawler.akka.messages.{TaskInfo, WorkersEvents}

/**
  * Created by Max on 24.10.2015.
  */
class Worker extends Actor with ActorLogging {
  val master = context.actorSelection("akka.tcp://cluster@127.0.0.1:2551/user/master")

  override def preStart(): Unit = {
     master ! WorkerUp()
   }

   override def receive: Receive = {
     case taskInfo: TaskInfo =>
       log.warning(s"Starting task $taskInfo")
   }
 }
