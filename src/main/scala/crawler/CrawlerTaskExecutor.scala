package crawler

import org.apache.mesos.MesosExecutorDriver

/**
 * Created by Max Petrov on 12.10.15.
 */
object CrawlerTaskExecutor {

  def main(args: Array[String]) {
    new MesosExecutorDriver(new CrawlerExecutor(args)).run()
  }
}
