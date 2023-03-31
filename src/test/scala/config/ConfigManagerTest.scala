package config

import config.ConfigManager
import org.scalatest.funsuite.AnyFunSuite
import com.typesafe.config.ConfigException

class ConfigManagerTest extends AnyFunSuite {
  test("Test getString with existent key") {
    // GIVEN
    val input = "tweeter.api_auth_bearer_token"
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

  test("Test getInt with existent key") {
    // GIVEN
    val input = "tweeter.max_results"
    val outputExpected = 10

    // WHEN
    val output = ConfigManager.getInt(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test getStringList") {
    // GIVEN
    val input = "tweeter.search_keywords"
    val outputExpected = List("crypto", "cryptocurrency", "bitcoin", "ethereum")

    // WHEN
    val output = ConfigManager.getStringList(input)

    // THEN
    assert(outputExpected == output)
  }
}
