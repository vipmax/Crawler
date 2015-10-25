package crawler.twitter

import java.io.File

import crawler.akka.messages.TaskInfo
import org.slf4j.LoggerFactory
import twitter4j._
import twitter4j.conf.ConfigurationBuilder

import scala.io.Source

/**
 * Created by Max Petrov on 15.10.15.
 */
object TwitterTest {
  val logger = LoggerFactory.getLogger("CrawlerScheduler")

  /*
  * returns Array[(String, String, String, String)] with  twitter credentials
  * */
  def getCredentials() = {
    new File("resources/twitterCreds")
      .listFiles()
      .flatMap(file => {
      Source.fromFile(file)
        .getLines()
        .filter(_.length > 10)
        .grouped(4)
        .map(group => new TwitterCredential(group(0).split("key=")(1), group(1).split("secret=")(1), group(2).split("token=")(1), group(3).split("token_secret=")(1)))
    })
  }

  def main(args: Array[String]) {
    println("Twitter test")

/*
    val listener = new TwitterListener()
    val credentials = TwitterTest.getCredentials()
    val cb: ConfigurationBuilder = new ConfigurationBuilder
    cb.setDebugEnabled(true).setOAuthConsumerKey(credentials(0)._1).setOAuthConsumerSecret(credentials(0)._2).setOAuthAccessToken(credentials(0)._3).setOAuthAccessTokenSecret(credentials(0)._4)
    val twitterStream = new TwitterStreamFactory(cb.build).getInstance
    twitterStream.addListener(listener)
    val fq = new FilterQuery
    val keywords = "NBA"
    fq.track(keywords)
    twitterStream.filter(fq)
*/


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
}