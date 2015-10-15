package crawler

import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.commons.MongoDBObject
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Paging, TwitterFactory}

import scala.collection.JavaConverters._

/**
 * Created by Max Petrov on 15.10.15.
 */
object TwitterTest {
  def main(args: Array[String]) {
    println("Twitter test")


    val cb = new ConfigurationBuilder()
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey("WTCtWipd1HPyi0g8WPkyPr6rN")
      .setOAuthConsumerSecret("dhGfb2a4b2Ur5MRRe9xFwHxfWJEold3EH3damrw0vocNIVXEJI")
      .setOAuthAccessToken("3142277661-rLc76RaBTizQL817WY5AKrKAuJpMjeCEYBUeKma")
      .setOAuthAccessTokenSecret("gMvYWDnpNedZ2r9PoEHY9h2eNgOiVFQPenPgZEfXBjtpb")

    val twitter = new TwitterFactory(cb.build()).getInstance()

    val tweets = twitter.getUserTimeline(new Paging(1, 5)).asScala
    tweets.foreach(println)

    val mongoClient = MongoClient("localhost", 27017)
    val db = mongoClient("test")
    val testCollection = db.getCollection("tweets")
    for (tweet <- tweets) {
      println("inserting " + ("tweet" -> tweet.getText))
      testCollection.insert(MongoDBObject("tweet" -> tweet.getText))
    }

    val allDocs = testCollection.find()
    println("All collection 'tweets'")
    println(allDocs.toArray.asScala.mkString("\n"))


  }
}