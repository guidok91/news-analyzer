import config.ConfigManager

@main
def main(args: String*): Unit = {
  val config = ConfigManager.config

  val bearerToken = config.getString("twitter_api.auth_bearer_token")
  val response = requests.get(
    url = "https://api.twitter.com/2/tweets/search/recent",
    params = Map(
      "query" -> "from:TwitterDev",
      "tweet.fields" -> "created_at",
      "expansions" -> "author_id",
      "user.fields" -> "created_at"
    ),
    headers = Map("Authorization" -> s"Bearer $bearerToken")
  )
  val response_data = ujson.read(response.text())("data").arr

  response_data.foreach(
    tweet => println(
      s"Tweet author ID: ${tweet("author_id")}\n" +
      s"Tweet text: ${tweet("text")}\n"
    )
  )
}
