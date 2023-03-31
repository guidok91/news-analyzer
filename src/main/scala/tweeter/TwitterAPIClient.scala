package tweeter

import requests.get
import org.json4s._
import org.json4s.jackson.JsonMethods._

class TwitterAPIClient(bearerToken: String) {

  def getTweets(
      tweetSearchKeywords: List[String],
      tweetFields: List[String],
      maxResults: Int
  ): List[Map[String, Any]] = {
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

  def extractTweets(response: String): List[Map[String, Any]] = {
    implicit val formats = org.json4s.DefaultFormats

    val response_parsed = parse(response).extract[Map[String, Any]]

    response_parsed.get("data") match {
      case Some(rows) => rows.asInstanceOf[List[Map[String, Any]]]
      case None =>
        throw NoDataFoundException(
          "No tweets found for the given search parameters"
        )
    }
  }
}

case class NoDataFoundException(message: String) extends Exception(message)
