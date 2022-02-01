import config.ConfigManager
import requests.get
import ujson.read

@main
def main(args: String*): Unit = {
  val config = ConfigManager.config

  val bearerToken = config.getString("twitter_api.auth_bearer_token")
  val response = requests.get(
    url = "https://api.twitter.com/2/tweets/search/recent",
    params = Map(
      "query" -> "from:TwitterDev",
      "tweet.fields" -> "id,text,created_at,geo,lang",
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
