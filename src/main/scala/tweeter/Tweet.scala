package tweeter

import sentiment.{Sentiment, SentimentAnalyzer}
import java.time.Instant

class Tweet(tweet_map: Map[String, Any]) {
  val id = tweet_map("id").asInstanceOf[String].toLong
  val author_id = tweet_map("author_id").asInstanceOf[String].toLong
  val text = tweet_map("text").asInstanceOf[String]
  val created_at =
    Instant.parse(tweet_map("created_at").asInstanceOf[String]).getEpochSecond
  val lang = tweet_map("lang").asInstanceOf[String]
  val sentiment = SentimentAnalyzer.getSentiment(text)
}
