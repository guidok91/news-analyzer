package tweeter

import sentiment.{Sentiment, SentimentAnalyzer}
import java.time.Instant

class Tweet(tweet: Map[String, Any]) {
  val id = tweet("id").asInstanceOf[String].toLong
  val author_id = tweet("author_id").asInstanceOf[String].toLong
  val text = tweet("text").asInstanceOf[String]
  val created_at =
    Instant.parse(tweet("created_at").asInstanceOf[String]).getEpochSecond
  val lang = tweet("lang").asInstanceOf[String]
  val sentiment = SentimentAnalyzer.getSentiment(text)
}
