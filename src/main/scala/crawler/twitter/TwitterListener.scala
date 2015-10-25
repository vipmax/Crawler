package crawler.twitter

import twitter4j.{StallWarning, Status, StatusDeletionNotice, StatusListener}

/**
 * Created by Max on 23.10.2015.
 */
class TwitterListener extends StatusListener{
  override def onStallWarning(stallWarning: StallWarning): Unit = ???

  override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = ???

  override def onScrubGeo(l: Long, l1: Long): Unit = ???

  override def onStatus(status: Status): Unit = {
    System.out.println("********************************************************")
    System.out.println(status.getCreatedAt + " @" + status.getUser.getScreenName + " : " + status.getText)
  }

  override def onTrackLimitationNotice(i: Int): Unit = ???

  override def onException(e: Exception): Unit = ???
}
