object Sentiment extends Enumeration {
  type Sentiment = Value
  val POSITIVE, NEGATIVE, NEUTRAL = Value

  def toSentiment(sentiment: Int): Sentiment = sentiment match {
    case 0 | 1 => Sentiment.NEGATIVE
    case 2     => Sentiment.NEUTRAL
    case 3 | 4 => Sentiment.POSITIVE
    case _ =>
      throw new MatchError(
        "Only integer sentiment values from 0 to 4 are accepted"
      )
  }
}
