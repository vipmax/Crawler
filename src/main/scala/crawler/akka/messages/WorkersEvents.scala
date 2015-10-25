package crawler.akka.messages


/**
 * Created by Max on 25.10.2015.
 */
object WorkersEvents {
  case class WorkerUp()
  case class WorkerDown()
}
