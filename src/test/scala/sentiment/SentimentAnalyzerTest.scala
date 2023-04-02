package sentiment

import sentiment.{Sentiment, SentimentAnalyzer}
import org.scalatest.funsuite.AnyFunSuite

class SentimentAnalyzerTest extends AnyFunSuite {
  test("Test getSentiment with POSITIVE sentence") {
    // GIVEN
    val input = "This pizza tastes amazing"
    val outputExpected = Sentiment.POSITIVE

    // WHEN
    val output = SentimentAnalyzer.getSentiment(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test getSentiment with NEUTRAL sentence") {
    // GIVEN
    val input = "Scala is a programming language"
    val outputExpected = Sentiment.NEUTRAL

    // WHEN
    val output = SentimentAnalyzer.getSentiment(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test getSentiment with NEGATIVE sentence") {
    // GIVEN
    val input = "I had a horrible experience in that restaurant"
    val outputExpected = Sentiment.NEGATIVE

    // WHEN
    val output = SentimentAnalyzer.getSentiment(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test getSentiment with multiple sentences") {
    // GIVEN
    val input =
      "Metal is a music genre. Metal is great. Metal has a positive impact on listeners."
    val outputExpected = Sentiment.POSITIVE

    // WHEN
    val output = SentimentAnalyzer.getSentiment(input)

    // THEN
    assert(outputExpected == output)
  }
}
