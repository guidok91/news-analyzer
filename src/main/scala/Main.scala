import collection.JavaConverters._
import config.ConfigManager
import api.TwitterAPIClient
import requests.get
import ujson.read

@main
def main(args: String*): Unit = {
  // TODO: refactor, modularise, handle exceptions
  val conf = ConfigManager.config
  val bearerToken = conf.getString("twitter_api.auth_bearer_token")
  val tweetKeywords = conf.getStringList("twitter_api.tweet_keywords_query").asScala.toList
  val tweetFields = conf.getStringList("twitter_api.tweet_fields").asScala.toList

  val twitterApiClient = new TwitterAPIClient(bearerToken)
  val tweets = twitterApiClient.getTweets(tweetKeywords, tweetFields)

  tweets.foreach(
    tweet => println(
      s"Tweet id: ${tweet("id")}\n" +
      s"Tweet text: ${tweet("text")}\n" +
      s"Tweet created_at: ${tweet("created_at")}\n" +
      s"Tweet lang: ${tweet("lang")}\n" +
      s"Tweet geo: ${tweet.obj.get("geo")}\n"
    )
  )
}
