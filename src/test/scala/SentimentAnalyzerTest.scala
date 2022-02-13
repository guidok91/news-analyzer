import org.scalatest.funsuite.AnyFunSuite

class SentimentAnalyzerTest extends AnyFunSuite {
  test("Test getSentiment with POSITIVE text") {
    // GIVEN
    val input = "This pizza tastes amazing"
    val outputExpected = Sentiment.POSITIVE

    // WHEN
    val output = SentimentAnalyzer.getSentiment(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test getSentiment with NEUTRAL text") {
    // GIVEN
    val input = "Scala is a programming language"
    val outputExpected = Sentiment.NEUTRAL

    // WHEN
    val output = SentimentAnalyzer.getSentiment(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test getSentiment with NEGATIVE text") {
    // GIVEN
    val input = "I had a horrible experience in that restaurant"
    val outputExpected = Sentiment.NEGATIVE

    // WHEN
    val output = SentimentAnalyzer.getSentiment(input)

    // THEN
    assert(outputExpected == output)
  }
}
