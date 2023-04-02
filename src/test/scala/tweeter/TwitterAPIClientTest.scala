package tweeter

import tweeter.{TwitterAPIClient, NoDataFoundException}
import org.scalatest.funsuite.AnyFunSuite

class TwitterAPIClientTest extends AnyFunSuite {
  val twitterApiClient = TwitterAPIClient("test token")

  test("Test buildSearchQuery with non-empty input") {
    // GIVEN
    val input = List("hashtag", "tweet")
    val outputExpected = "hashtag OR tweet OR #hashtag OR #tweet"

    // WHEN
    val output = twitterApiClient.buildSearchQuery(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test buildSearchQuery with empty input") {
    // GIVEN
    val input = List()

    // THEN
    assertThrows[NoDataFoundException] {
      twitterApiClient.buildSearchQuery(input)
    }
  }

  test("Test extractTweets with non-empty input data") {
    // GIVEN
    val input = """{
      "data": [
        {"id": "123", "text": "tweet1"},
        {"id": "456", "text": "tweet2"}
      ],
      "meta": {
        "newest_id": "1373001119480344583",
        "oldest_id": "1364275610764201984",
        "result_count": 2
      }
    }"""
    val outputExpected = List(
      Map("id" -> "123", "text" -> "tweet1"),
      Map("id" -> "456", "text" -> "tweet2")
    )

    // WHEN
    val output = twitterApiClient.extractTweets(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test extractTweets with empty input data") {
    // GIVEN
    val input = """{
      "meta": {
        "newest_id": "1373001119480344583",
        "oldest_id": "1364275610764201984",
        "result_count": 0
      }
    }"""

    // THEN
    assertThrows[NoDataFoundException] {
      twitterApiClient.extractTweets(input)
    }
  }
}
