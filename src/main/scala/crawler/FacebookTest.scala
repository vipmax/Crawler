package crawler

import facebook4j.FacebookFactory
import facebook4j.auth.AccessToken
import scala.collection.JavaConverters._


/**
 * Created by Max Petrov on 19.10.2015.
 */
object FacebookTest {
  def main(args: Array[String]) {
    val facebook = new FacebookFactory().getInstance
    facebook.setOAuthAppId("", "")
    facebook.setOAuthAccessToken(new AccessToken("1128068063883549|1AVqo4RT2CAFnQ32412wkYJLWbY"))
    val results = facebook.getFeed("Reebok").asScala
    results.foreach(println)
  }
}
