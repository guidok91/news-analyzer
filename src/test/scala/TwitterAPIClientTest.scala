import org.scalatest.funsuite.AnyFunSuite

class TwitterAPIClientTest extends AnyFunSuite {
  val twitterApiClient = new TwitterAPIClient("test token")

  test("Test buildSearchQuery with non-empty input") {
    val input = List("hashtag", "tweet")
    val outputExpected = "hashtag OR tweet OR #hashtag OR #tweet"
    val output = twitterApiClient.buildSearchQuery(input)
    assert(outputExpected == output)
  }

  test("Test buildSearchQuery with empty input") {
    val input = List()
    assertThrows[NoDataFoundException] {
      twitterApiClient.buildSearchQuery(input)
    }
  }

  test("Test extractTweets with non-empty input data") {
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
    val outputExpected = List(Map("id" -> "123", "text" -> "tweet1"), Map("id" -> "456", "text" -> "tweet2"))
    val output = twitterApiClient.extractTweets(input)
    assert(outputExpected == output)
  }

  test("Test extractTweets with empty input data") {
    val input = """{
      "meta": {
        "newest_id": "1373001119480344583",
        "oldest_id": "1364275610764201984",
        "result_count": 0
      }
    }"""
    assertThrows[NoDataFoundException] {
      twitterApiClient.extractTweets(input)
    }
  }

  // TODO test getTweets (mocking requests.get)
}
