package api
import ujson.Value
import requests.{get, Response}
import ujson.read
import scala.collection.mutable.ArrayBuffer

class TwitterAPIClient(bearerToken: String) {

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

    extractTweets(response.text())
  }

  def buildSearchQuery(tweetKeywords: List[String]): String = {
    (tweetKeywords ++ tweetKeywords.map(t => s"#$t")).mkString(" OR ")
  }

  def extractTweets(response: String): ArrayBuffer[ujson.Value] = {
    ujson.read(response).obj.get("data") match {
      case Some(rows) => rows.arr
      case None => throw new NoDataFoundException("No tweets found for the given search parameters")
    }
  }
}

case class NoDataFoundException(message: String) extends Exception(message)
