package config

import config.ConfigManager
import org.scalatest.funsuite.AnyFunSuite
import com.typesafe.config.ConfigException

class ConfigManagerTest extends AnyFunSuite {
  val configManager = ConfigManager("src/test/resources/application.conf")

  test("Test getString with existent key") {
    // GIVEN
    val input = "categ.string_key"
    val outputExpected = "string_value"

    // WHEN
    val output = configManager.getString(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test getString with inexistent key") {
    // GIVEN
    val input = "categ.string_key_inexistent"

    // THEN
    assertThrows[ConfigException] {
      configManager.getString(input)
    }
  }

  test("Test getInt with existent key") {
    // GIVEN
    val input = "categ.int_key"
    val outputExpected = 5

    // WHEN
    val output = configManager.getInt(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test getStringList") {
    // GIVEN
    val input = "categ.string_list_key"
    val outputExpected = List("string_value_1", "string_value_2")

    // WHEN
    val output = configManager.getStringList(input)

    // THEN
    assert(outputExpected == output)
  }
}
