package crawler

import org.apache.mesos.MesosSchedulerDriver
import org.apache.mesos.Protos.FrameworkInfo

/**
 * Created by Max Petrov on 12.10.15.
 */
object CrawlerMain {

  def main(args: Array[String]) {
    println("Starting crawler")

    val framework = FrameworkInfo.newBuilder.
      setName("crawler").
      setUser("").
      setRole("*").
      setCheckpoint(false).
      setFailoverTimeout(0.0d).
      build()

    val scheduler = new CrawlerScheduler
    val masterUrl: String = "192.168.0.31:5050"

    new MesosSchedulerDriver(scheduler, framework, masterUrl).run()
  }
}
