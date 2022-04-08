package test.e2e.org.rockem.kafcar.support

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.LongDeserializer
import org.apache.kafka.common.serialization.StringDeserializer

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG


class KafkaMonitor {

    KafkaMonitor() {
        this.consumer = createE2EConsumer()
    }

    def start() {
        final Properties props = new Properties()
        props.with {
            it.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            it.put(GROUP_ID_CONFIG, "e2e-test")
        }
        // props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // props.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Create the consumer using props.
        final Consumer<String, String> consumer = new KafkaConsumer<>(props)

    }

    boolean hasReceived(Message message) {
        return false
    }

    def stop() {
    }

    Consumer<String, String> createE2EConsumer() {
        final Properties props = new Properties()
        props.with {
            it.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            it.put(GROUP_ID_CONFIG, "e2e-test")
        }
        // props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // props.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Create the consumer using props.
        final Consumer<String, String> consumer = new KafkaConsumer<>(props)
        consumer.subscribe(['e2e.output.topic'])
        return consumer
    }
}
