package crawler.akka.actors

import akka.actor.{Actor, ActorLogging}
import scala.concurrent.duration._
/**
  * Created by Max on 24.10.2015.
  */

case class WorkerUp()

class WorkerActor(masterIp: String) extends Actor with ActorLogging {
  val master = context.actorSelection(s"akka.tcp://cluster@$masterIp:2551/user/master")

  override def preStart() = {
    0 to 4 foreach{e => Thread.sleep(1000); println(5-e)}
    println(s"Sending task request")

    master ! WorkerUp()
  }

  override def receive() = {
    case taskInfo: Task =>
      println(s"Received task")
      println(s"Starting task $taskInfo")
      println(s"Ending task $taskInfo")
  }
 }
