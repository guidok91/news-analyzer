@main
def main(args: String*): Unit = {
  val tweets = getTweets()
  produceTweetsToKafka(tweets)
}

private def getTweets(): List[Map[String, Any]] = {
  val bearerToken = ConfigManager.getString("tweeter.api_auth_bearer_token")
  val tweetSearchKeywords =
    ConfigManager.getStringList("tweeter.search_keywords")
  val tweetFields = ConfigManager.getStringList("tweeter.fields")
  val maxResults = ConfigManager.getInt("tweeter.max_results")

  val twitterApiClient = TwitterAPIClient(bearerToken)

  twitterApiClient
    .getTweets(tweetSearchKeywords, tweetFields, maxResults)
    .map(tweet =>
      tweet + ("sentiment" -> SentimentAnalyzer
        .getSentiment(tweet("text").asInstanceOf[String]))
    )
}

private def produceTweetsToKafka(tweets: List[Map[String, Any]]): Unit = {
  val topic = ConfigManager.getString("kafka.topic")
  val brokerUrl = ConfigManager.getString("kafka.broker_url")
  val schemaRegistryUrl = ConfigManager.getString("kafka.schema_registry_url")
  val avroSchema =
    scala.io.Source.fromFile("conf/tweet-sentiment-avro-schema.avsc").mkString

  EventProducer(topic, avroSchema, brokerUrl, schemaRegistryUrl).produce(tweets)
}
