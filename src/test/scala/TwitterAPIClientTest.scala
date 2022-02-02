import org.scalatest.funsuite.AnyFunSuite

class TwitterAPIClientTest extends AnyFunSuite {
  val twitterApiClient = new TwitterAPIClient("test token")

  test("Test buildSearchQuery with non-empty input") {
    val tweetKeywords = List("hashtag", "tweet")
    assert(twitterApiClient.buildSearchQuery(tweetKeywords) == "hashtag OR tweet OR #hashtag OR #tweet")
  }

  test("Test buildSearchQuery with empty input") {
    val tweetKeywords = List()
    assertThrows[NoDataFoundException] {
      twitterApiClient.buildSearchQuery(tweetKeywords)
    }
  }

  // TODO test extractTweets and getTweets (mocking requests.get)
}
