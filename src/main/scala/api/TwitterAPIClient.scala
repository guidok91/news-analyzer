package api
import requests.{get, Response}
import play.api.libs.json.Json

class TwitterAPIClient(bearerToken: String) {

  def getTweets(tweetKeywords: List[String], tweetFields: List[String]): List[Map[String, String]] = {
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

  def extractTweets(response: String): List[Map[String, String]] = {
    val response_json = Json.parse(response)
    
    (response_json \ "data").asOpt[List[Map[String, String]]] match {
      case Some(rows) => rows
      case None => throw new NoDataFoundException("No tweets found for the given search parameters")
    }
  }
}

case class NoDataFoundException(message: String) extends Exception(message)
