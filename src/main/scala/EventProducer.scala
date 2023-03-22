import java.time.Instant
import java.util.Properties
import org.apache.avro.Schema.Parser
import org.apache.avro.generic.GenericData
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class EventProducer(
    topic: String,
    avroSchema: String,
    brokerUrl: String,
    schemaRegistryUrl: String
) {
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

  val producer = KafkaProducer[String, GenericData.Record](props)

  def produce(tweets: List[Map[String, Any]]): Unit = {
    for (tweet <- tweets) {
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
      avroRecord.put("lang", tweet("id").asInstanceOf[String])
      avroRecord.put("sentiment", tweet("id").asInstanceOf[String])

      val record = ProducerRecord(
        topic,
        tweet("author_id").asInstanceOf[String],
        avroRecord
      )
      val ack = producer.send(record).get()
      println(s"${ack.toString} written to partition ${ack.partition.toString}")
    }
  }
}
