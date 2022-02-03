import org.scalatest.funsuite.AnyFunSuite

class TwitterAPIClientTest extends AnyFunSuite {
  val twitterApiClient = new TwitterAPIClient("test token")

  test("Test buildSearchQuery with non-empty input") {
    val inputTweetKeywords = List("hashtag", "tweet")
    val outputExpected = "hashtag OR tweet OR #hashtag OR #tweet"
    val output = twitterApiClient.buildSearchQuery(inputTweetKeywords)
    assert(outputExpected == output)
  }

  test("Test buildSearchQuery with empty input") {
    val inputTweetKeywords = List()
    assertThrows[NoDataFoundException] {
      twitterApiClient.buildSearchQuery(inputTweetKeywords)
    }
  }

  test("Test extractTweets with non-empty input") {
    val inputResponse = """{
      "data": [{"id": "123", "text": "tweet1"}, {"id": "456", "text": "tweet2"}]
    }"""
    val outputExpected = List(Map("id" -> "123", "text" -> "tweet1"), Map("id" -> "456", "text" -> "tweet2"))
    val output = twitterApiClient.extractTweets(inputResponse)
    assert(outputExpected == output)
  }

  // TODO test extractTweets and getTweets (mocking requests.get)
}
