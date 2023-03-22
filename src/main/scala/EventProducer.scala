import java.util.Properties

import org.apache.avro.Schema.Parser
import org.apache.avro.generic.GenericData
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.slf4j.LoggerFactory

class EventProducer(
    topic: String,
    avroSchema: String,
    brokerUrl: String,
    schemaRegistryUrl: String
) {
  val props = new Properties()
  props.put("bootstrap.servers", brokerUrl)
  props.put("schema.registry.url", schemaRegistryUrl)
  props.put(
    "value.serializer",
    "io.confluent.kafka.serializers.KafkaAvroSerializer"
  )
  props.put("acks", "1")

  val producer = new KafkaProducer[String, GenericData.Record](props)

  def produce(tweets: List[Map[String, Any]]): Unit = {

    for (tweet <- tweets) {
      val avroRecord = new GenericData.Record(Parser().parse(avroSchema))
      avroRecord.put("id", tweet("id").asInstanceOf[Int])
      // TODO put other fields, converting them if necessary

      val record = new ProducerRecord(topic, avroRecord)
      val ack = producer.send(record).get()
      println(s"${ack.toString} written to partition ${ack.partition.toString}")
    }
  }
}
