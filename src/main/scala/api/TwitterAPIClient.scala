package api
import ujson.Value
import requests.{get, Response}
import ujson.read
import scala.collection.mutable.ArrayBuffer

class TwitterAPIClient(bearerToken: String){

  def getTweets(tweetKeywords: List[String], tweetFields: List[String]) : ArrayBuffer[ujson.Value] = {
    val searchQuery = buildSearchQuery(tweetKeywords)
    val tweetFieldsStr = tweetFields.mkString(",")

    val response = requests.get(
      url = "https://api.twitter.com/2/tweets/search/recent",
      params = Map(
        "query" -> searchQuery,
        "tweet.fields" -> tweetFieldsStr,
      ),
      headers = Map("Authorization" -> s"Bearer $bearerToken")
    )

    formatResponse(response)
  }

  private def buildSearchQuery(tweetKeywords: List[String]): String = {
    (tweetKeywords ++ tweetKeywords.map(t => s"#$t")).mkString(" OR ")
  }

  private def formatResponse(response: requests.Response): ArrayBuffer[ujson.Value]  = {
    ujson.read(response.text())("data").arr
  }
}
