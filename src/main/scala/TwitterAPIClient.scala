import requests.get
import play.api.libs.json.Json

class TwitterAPIClient(bearerToken: String) {

  def getTweets(
      tweetSearchKeywords: List[String],
      tweetFields: List[String],
      maxResults: Int
  ): List[Map[String, String]] = {
    val searchQuery = buildSearchQuery(tweetSearchKeywords)
    val tweetFieldsStr = tweetFields.mkString(",")

    val response = requests.get(
      url = "https://api.twitter.com/2/tweets/search/recent",
      params = Map(
        "query" -> searchQuery,
        "tweet.fields" -> tweetFieldsStr,
        "max_results" -> maxResults.toString
      ),
      headers = Map("Authorization" -> s"Bearer $bearerToken")
    )

    extractTweets(response.text())
  }

  def buildSearchQuery(tweetSearchKeywords: List[String]): String = {
    if tweetSearchKeywords.isEmpty then
      throw NoDataFoundException(
        "At least one tweet search keyword must be specified"
      )
    (tweetSearchKeywords ++ tweetSearchKeywords.map(t => s"#$t"))
      .mkString(" OR ")
  }

  def extractTweets(response: String): List[Map[String, String]] = {
    val response_json = Json.parse(response)

    (response_json \ "data").asOpt[List[Map[String, String]]] match {
      case Some(rows) => rows
      case None =>
        throw new NoDataFoundException(
          "No tweets found for the given search parameters"
        )
    }
  }
}

case class NoDataFoundException(message: String) extends Exception(message)
