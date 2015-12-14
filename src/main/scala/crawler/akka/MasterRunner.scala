package crawler.akka

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import crawler.akka.actors.MasterActor


object MasterRunner  {

  def main(args: Array[String]) {

    val masterIp = if (args.length > 0) args(0) else "127.0.0.1"
    val myIp = if (args.length > 1) args(1) else "127.0.0.1"

    val config: String = s"""
      akka {
          actor {
            provider = "akka.cluster.ClusterActorRefProvider"
          }

          remote {
            log-remote-lifecycle-events = off
            netty.tcp {
              hostname = "$myIp"
              port = 2551
            }
          }

          cluster {
            seed-nodes = ["akka.tcp://cluster@$masterIp:2551"]
             auto-down-unreachable-after = 1s
          }
      }
                           """

    val system = ActorSystem("cluster", ConfigFactory.parseString(config))
    system.actorOf(Props[MasterActor], "master")
    system.whenTerminated
  }

}