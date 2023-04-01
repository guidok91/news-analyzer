package kafka

import sentiment.Sentiment
import tweeter.Tweet
import java.util.Properties
import org.apache.avro.Schema.Parser
import org.apache.avro.generic.GenericData
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.clients.producer.{Callback, RecordMetadata}
import org.apache.kafka.common.serialization.StringSerializer
import io.confluent.kafka.serializers.KafkaAvroSerializer

class KafkaTweetProducer(
    topic: String,
    avroSchema: String,
    brokerUrl: String,
    schemaRegistryUrl: String
) {
  val producer = buildProducer()

  def produce(tweets: List[Tweet]): Unit = {
    for (tweet <- tweets) {
      val record = ProducerRecord(
        topic,
        tweet.author_id.toString(),
        buildAvroRecord(tweet)
      )
      producer.send(record, new ProducerCallback)
    }
  }

  private def buildProducer(): KafkaProducer[String, GenericData.Record] = {
    val props = Properties()

    props.put("bootstrap.servers", brokerUrl)
    props.put("schema.registry.url", schemaRegistryUrl)
    props.put("key.serializer", classOf[StringSerializer].getName)
    props.put("value.serializer", classOf[KafkaAvroSerializer].getName)
    props.put("acks", "1")

    KafkaProducer[String, GenericData.Record](props)
  }

  private def buildAvroRecord(tweet: Tweet): GenericData.Record = {
    val avroRecord = GenericData.Record(Parser().parse(avroSchema))

    avroRecord.put("id", tweet.id)
    avroRecord.put("author_id", tweet.author_id)
    avroRecord.put("text", tweet.text)
    avroRecord.put("created_at", tweet.created_at)
    avroRecord.put("lang", tweet.lang)
    avroRecord.put(
      "sentiment",
      tweet.sentiment.asInstanceOf[Sentiment.Value].toString()
    )

    avroRecord
  }
}

private class ProducerCallback extends Callback {
  @Override
  override def onCompletion(
      metadata: RecordMetadata,
      exception: Exception
  ): Unit = {
    exception.match {
      case null =>
        println(s"Written record to topic ${metadata.topic()} on ${metadata
            .timestamp()} to partition ${metadata.partition()}")
      case e =>
        println(
          s"There was an error producing the record. Stack trace: ${e.printStackTrace()}"
        )
    }
  }
}
