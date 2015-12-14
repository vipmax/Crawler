package crawler.akka.actors

import akka.actor.{Actor, ActorLogging}

/**
  * Created by Max on 24.10.2015.
  */

case class Task(taskInfo: String)

class MasterActor extends Actor with ActorLogging {

   override def receive: Receive = {
     case WorkerUp() =>
       println(s"$sender Wants task")
       sender ! Task("Some task")
   }

 }
