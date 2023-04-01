import tweeter.{Tweet, TwitterAPIClient}
import config.ConfigManager
import kafka.KafkaTweetProducer

@main
def main(args: String*): Unit = {
  val tweets = getAndEnrichTweets()
  produceTweetsToKafka(tweets)
}

private def getAndEnrichTweets(): List[Tweet] = {
  val bearerToken = ConfigManager.getString("tweeter.api_auth_bearer_token")
  val tweetSearchKeywords =
    ConfigManager.getStringList("tweeter.search_keywords")
  val tweetFields = ConfigManager.getStringList("tweeter.fields")
  val maxResults = ConfigManager.getInt("tweeter.max_results")

  val twitterApiClient = TwitterAPIClient(bearerToken)

  twitterApiClient
    .getTweets(tweetSearchKeywords, tweetFields, maxResults)
    .map(tweet => Tweet(tweet))
}

private def produceTweetsToKafka(tweets: List[Tweet]): Unit = {
  val topic = ConfigManager.getString("kafka.topic")
  val brokerUrl = ConfigManager.getString("kafka.broker_url")
  val schemaRegistryUrl = ConfigManager.getString("kafka.schema_registry_url")
  val avroSchema =
    scala.io.Source
      .fromFile("src/main/resources/tweet-sentiment-avro-schema.avsc")
      .mkString

  KafkaTweetProducer(topic, avroSchema, brokerUrl, schemaRegistryUrl).produce(
    tweets
  )
}
