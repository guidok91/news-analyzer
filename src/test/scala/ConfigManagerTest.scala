import org.scalatest.funsuite.AnyFunSuite
import com.typesafe.config.ConfigException

class ConfigManagerTest extends AnyFunSuite {
  test("Test getString with existent key") {
    // GIVEN
    val input = "twitter_api.auth_bearer_token"
    val outputExpected = "<PLACEHOLDER>"

    // WHEN
    val output = ConfigManager.getString(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test getString with inexistent key") {
    // GIVEN
    val input = "inexistent_key"

    // THEN
    assertThrows[ConfigException] {
      ConfigManager.getString(input)
    }
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
