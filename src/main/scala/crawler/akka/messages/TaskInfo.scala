package crawler.akka.messages

import crawler.twitter.TwitterCredential

/**
 * Created by Max on 25.10.2015.
 */
case class TaskInfo(cred: TwitterCredential, keyWords: List[String])
