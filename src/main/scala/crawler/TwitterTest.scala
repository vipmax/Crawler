package crawler

import java.io.File

import org.slf4j.LoggerFactory
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Query, TwitterFactory}

import scala.collection.JavaConverters._
import scala.io.Source

/**
 * Created by Max Petrov on 15.10.15.
 */
object TwitterTest {
  val logger = LoggerFactory.getLogger("CrawlerScheduler")

  def main(args: Array[String]) {
    println("Twitter test")

    val credentials = getCredentials()


    val cb = new ConfigurationBuilder()
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(credentials(0)_1)
      .setOAuthConsumerSecret(credentials(0)_2)
      .setOAuthAccessToken(credentials(0)_3)
      .setOAuthAccessTokenSecret(credentials(0)_4)

    val twitter = new TwitterFactory(cb.build()).getInstance()

    //    val tweets = twitter.getUserTimeline(new Paging(1, 5)).asScala
    val tweets = twitter.search(new Query("IGIL").count(1)).getTweets.asScala
    tweets.foreach(println)

    //    val mongoClient = MongoClient("localhost", 27017)
    //    val db = mongoClient("test")
    //    val testCollection = db.getCollection("tweets")
    //    for (tweet <- tweets) {
    //      println("inserting " + ("tweet" -> tweet.getText))
    //      testCollection.insert(MongoDBObject("tweet" -> tweet.getText))
    //    }
    //
    //    val allDocs = testCollection.find()
    //    println("All collection 'tweets'")
    //    println(allDocs.toArray.asScala.mkString("\n"))


  }

  /*
  * returns Array[(String, String, String, String)] with  twitter credentials
  * */
  def getCredentials() = {
    new File("~/Crawler/resources/twitterCreds")
      .listFiles()
      .flatMap(file => {
        Source.fromFile(file)
          .getLines()
          .filter(_.length > 10)
          .grouped(4)
          .map(group => (group(0).split("key=")(1), group(1).split("secret=")(1), group(2).split("token=")(1), group(3).split("token_secret=")(1)))
      })
  }
}