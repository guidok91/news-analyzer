package kafka

import sentiment.Sentiment
import java.time.Instant
import java.util.Properties
import org.apache.avro.Schema.Parser
import org.apache.avro.generic.GenericData
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class KafkaTweetProducer(
    topic: String,
    avroSchema: String,
    brokerUrl: String,
    schemaRegistryUrl: String
) {
  val producer = buildProducer()

  def produce(tweets: List[Map[String, Any]]): Unit = {
    for (tweet <- tweets) {
      val record = ProducerRecord(
        topic,
        tweet("author_id").asInstanceOf[String],
        buildAvroRecord(tweet)
      )
      val ack = producer.send(record).get()
      println(s"${ack.toString} written to partition ${ack.partition.toString}")
    }
  }

  private def buildProducer(): KafkaProducer[String, GenericData.Record] = {
    val props = Properties()

    props.put("bootstrap.servers", brokerUrl)
    props.put("schema.registry.url", schemaRegistryUrl)
    props.put(
      "key.serializer",
      "io.confluent.kafka.serializers.KafkaAvroSerializer"
    )
    props.put(
      "value.serializer",
      "io.confluent.kafka.serializers.KafkaAvroSerializer"
    )
    props.put("acks", "1")

    KafkaProducer[String, GenericData.Record](props)
  }

  private def buildAvroRecord(tweet: Map[String, Any]): GenericData.Record = {
    val avroRecord = GenericData.Record(Parser().parse(avroSchema))

    avroRecord.put("id", tweet("id").asInstanceOf[String].toLong)
    avroRecord.put(
      "author_id",
      tweet("author_id").asInstanceOf[String].toLong
    )
    avroRecord.put("text", tweet("text").asInstanceOf[String])
    avroRecord.put(
      "created_at",
      Instant.parse(tweet("created_at").asInstanceOf[String]).getEpochSecond
    )
    avroRecord.put("lang", tweet("lang").asInstanceOf[String])
    avroRecord.put(
      "sentiment",
      tweet("sentiment").asInstanceOf[Sentiment.Value].toString()
    )

    avroRecord
  }
}
