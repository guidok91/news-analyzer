import org.scalatest.funsuite.AnyFunSuite

class SentimentTest extends AnyFunSuite {
  test("Test toSentiment with sentiment = 0") {
    // GIVEN
    val input = 0
    val outputExpected = Sentiment.NEGATIVE

    // WHEN
    val output = Sentiment.toSentiment(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test toSentiment with sentiment = 1") {
    // GIVEN
    val input = 1
    val outputExpected = Sentiment.NEGATIVE

    // WHEN
    val output = Sentiment.toSentiment(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test toSentiment with sentiment = 2") {
    // GIVEN
    val input = 2
    val outputExpected = Sentiment.NEUTRAL

    // WHEN
    val output = Sentiment.toSentiment(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test toSentiment with sentiment = 3") {
    // GIVEN
    val input = 3
    val outputExpected = Sentiment.POSITIVE

    // WHEN
    val output = Sentiment.toSentiment(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test toSentiment with sentiment = 4") {
    // GIVEN
    val input = 4
    val outputExpected = Sentiment.POSITIVE

    // WHEN
    val output = Sentiment.toSentiment(input)

    // THEN
    assert(outputExpected == output)
  }

  test("Test toSentiment with invalid sentiment") {
    // GIVEN
    val input = 5

    // THEN
    assertThrows[MatchError] {
      Sentiment.toSentiment(input)
    }
  }
}
