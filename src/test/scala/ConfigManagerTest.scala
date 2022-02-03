import org.scalatest.funsuite.AnyFunSuite

class ConfigManagerTest extends AnyFunSuite {
  test("Test getString") {
    // GIVEN
    val input = "twitter_api.auth_bearer_token"
    val outputExpected = "<PLACEHOLDER>"

    // WHEN
    val output = ConfigManager.getString(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test getStringList") {
    // GIVEN
    val input = "twitter_api.tweet_keywords_query"
    val outputExpected = List("covid", "covid19", "coronavirus")

    // WHEN
    val output = ConfigManager.getStringList(input)

    // THEN
    assert(outputExpected == output)
  }
}
