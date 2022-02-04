@main
def main(args: String*): Unit = {
  val bearerToken = ConfigManager.getString("auth_bearer_token")
  val tweetKeywords =
    ConfigManager.getStringList("tweet_keywords_query")
  val tweetFields = ConfigManager.getStringList("tweet_fields")
  val maxResults = ConfigManager.getInt("tweet_max_results")

  val twitterApiClient = new TwitterAPIClient(bearerToken)
  val tweets =
    twitterApiClient.getTweets(tweetKeywords, tweetFields, maxResults)

  tweets.foreach(tweet =>
    println(
      s"Tweet id: ${tweet("id")}\n" +
        s"Tweet text: ${tweet("text")}\n" +
        s"Tweet created_at: ${tweet("created_at")}\n" +
        s"Tweet lang: ${tweet("lang")}\n"
    )
  )
}
