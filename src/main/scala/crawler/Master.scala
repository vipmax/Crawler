package crawler

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
 * Created by Max on 24.10.2015.
 */
class Master extends Actor with ActorLogging {

  override def receive: Receive = {
    case "Hi" =>
      log.warning("Have got Hi *****************************************************")
  }

}


object StartMaster extends App {
  private val config: String = """akka {
      actor {
        provider = "akka.cluster.ClusterActorRefProvider"
      }

      remote {
        log-remote-lifecycle-events = off
        netty.tcp {
          hostname = "127.0.0.1"
          port = 2551
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
  val system = ActorSystem("cluster", ConfigFactory.parseString(config))
  system.actorOf(Props[Master], "master")
  system.whenTerminated
}