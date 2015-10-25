package crawler.mesos

import org.apache.mesos.MesosSchedulerDriver
import org.apache.mesos.Protos.FrameworkInfo

/**
 * Created by Max Petrov on 12.10.15.
 */
object CrawlerMain {

  def main(args: Array[String]) {
    println("Starting crawler at " + args(0))

    val framework = FrameworkInfo.newBuilder.
      setName("crawler").
      setUser("").
      setRole("*").
      setCheckpoint(false).
      setFailoverTimeout(0.0d).
      build()

    val scheduler = new CrawlerScheduler
    val masterUrl: String = args(0) + ":5050"

    new MesosSchedulerDriver(scheduler, framework, masterUrl).run()
  }
}
