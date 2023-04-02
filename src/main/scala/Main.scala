import tweeter.{Tweet, TwitterAPIClient}
import config.ConfigManager
import kafka.KafkaTweetProducer

@main
def main(args: String*): Unit = {
  val configManager = ConfigManager("src/main/resources/application.conf")
  val tweets = getAndEnrichTweets(configManager)
  produceTweetsToKafka(tweets, configManager)
}

private def getAndEnrichTweets(configManager: ConfigManager): List[Tweet] = {
  val bearerToken = configManager.getString("tweeter.api_auth_bearer_token")
  val tweetSearchKeywords =
    configManager.getStringList("tweeter.search_keywords")
  val tweetFields = configManager.getStringList("tweeter.fields")
  val maxResults = configManager.getInt("tweeter.max_results")

  val twitterApiClient = TwitterAPIClient(bearerToken)

  twitterApiClient
    .getTweets(tweetSearchKeywords, tweetFields, maxResults)
    .map(tweet => Tweet(tweet))
}

private def produceTweetsToKafka(
    tweets: List[Tweet],
    configManager: ConfigManager
): Unit = {
  val topic = configManager.getString("kafka.topic")
  val brokerUrl = configManager.getString("kafka.broker_url")
  val schemaRegistryUrl = configManager.getString("kafka.schema_registry_url")
  val avroSchema =
    scala.io.Source
      .fromFile("src/main/resources/tweet-sentiment-avro-schema.avsc")
      .mkString

  KafkaTweetProducer(topic, avroSchema, brokerUrl, schemaRegistryUrl).produce(
    tweets
  )
}
