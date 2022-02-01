import collection.JavaConverters._
import config.ConfigManager
import requests.get
import ujson.read

@main
def main(args: String*): Unit = {
  // TODO: refactor, modularise, handle exceptions
  val conf = ConfigManager.config

  val tweetKeywords = conf.getStringList("twitter_api.tweet_keywords_query").asScala.toList
  val searchQuery = (tweetKeywords ++ tweetKeywords.map(t => s"#$t")).mkString(" OR ")

  val bearerToken = conf.getString("twitter_api.auth_bearer_token")
  val response = requests.get(
    url = "https://api.twitter.com/2/tweets/search/recent",
    params = Map(
      "query" -> searchQuery,
      "tweet.fields" -> "id,text,created_at,lang",
    ),
    headers = Map("Authorization" -> s"Bearer $bearerToken")
  )
  val response_data = ujson.read(response.text())("data").arr

  response_data.foreach(
    tweet => println(
      s"Tweet id: ${tweet("id")}\n" +
      s"Tweet text: ${tweet("text")}\n" +
      s"Tweet created_at: ${tweet("created_at")}\n" +
      s"Tweet lang: ${tweet("lang")}\n" +
      s"Tweet geo: ${tweet.obj.get("geo")}\n"
    )
  )
}
