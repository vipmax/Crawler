package crawler

import akka.actor.{Props, ActorSystem, Actor, ActorLogging}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent.{UnreachableMember, MemberEvent, InitialStateAsEvents}
import com.typesafe.config.ConfigFactory

/**
 * Created by Max on 24.10.2015.
 */
class Worker extends Actor with ActorLogging {

  override def preStart(): Unit = {
    context.actorSelection("akka.tcp://cluster@127.0.0.1:2551/user/master") ! "Hi"
  }

  override def receive: Receive = {
    case _ =>
  }
}


object StartWorker extends App {
  private val config: String = """akka {
      actor {
        provider = "akka.cluster.ClusterActorRefProvider"
      }

      remote {
        log-remote-lifecycle-events = off
        netty.tcp {
          hostname = "127.0.0.1"
          port = 2552
        }
      }

      cluster {
        seed-nodes = [
          "akka.tcp://cluster@127.0.0.1:2551",
          "akka.tcp://cluster@127.0.0.1:2552"]

        # auto-down-unreachable-after = 10s
      }
    }
                               """


  val system = ActorSystem("cluster",ConfigFactory.parseString(config))
  system.actorOf(Props[Worker], "worker")
  system.whenTerminated
}