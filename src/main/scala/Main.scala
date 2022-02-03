@main
def main(args: String*): Unit = {
  val bearerToken = ConfigManager.getString("twitter_api.auth_bearer_token")
  val tweetKeywords = ConfigManager.getStringList("twitter_api.tweet_keywords_query")
  val tweetFields = ConfigManager.getStringList("twitter_api.tweet_fields")

  val twitterApiClient = new TwitterAPIClient(bearerToken)
  val tweets = twitterApiClient.getTweets(tweetKeywords, tweetFields)

  tweets.foreach(tweet =>
    println(
      s"Tweet id: ${tweet("id")}\n" +
        s"Tweet text: ${tweet("text")}\n" +
        s"Tweet created_at: ${tweet("created_at")}\n" +
        s"Tweet lang: ${tweet("lang")}\n"
    )
  )
}
