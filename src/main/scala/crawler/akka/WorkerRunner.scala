package crawler.akka

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import crawler.akka.actors.Worker


object WorkerRunner extends App {
  private val config: String = """akka {
      actor {
        provider = "akka.cluster.ClusterActorRefProvider"
      }

      remote {
        log-remote-lifecycle-events = off
        netty.tcp {
          hostname = "127.0.0.1"
          port = 0
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