package test.e2e.org.rockem.kafcar.support

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer

import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG

class KafkaMonitor {


    public static final String BOOTSTRAP_SERVERS = "localhost:9092"

    private List<Message> dispatchedMessages = []
    private boolean running = true
    private ExecutorService consumerThread =  Executors.newSingleThreadExecutor()

    def start() {
        consumerThread.execute({ runConsumer() })
    }

    private void runConsumer() {
        final Consumer<String, String> consumer = createE2EConsumer()

        while (running) {
            final ConsumerRecords<String, String> consumerRecords =
                    consumer.poll(Duration.of(1, ChronoUnit.SECONDS))

            consumerRecords.each {
                dispatchedMessages.add(createMessageFrom(it))
            }

            consumer.commitAsync()
        }
        consumer.close()
    }

    Message createMessageFrom(ConsumerRecord<String, String> record) {
        return new Message(
                topic: 'e2e.output.topic',
                key: record.key(),
                value: record.value()
        )
    }

    boolean hasReceived(Message message) {
        return dispatchedMessages.find { (it == message) } != null
    }

    def stop() {
        running = false
    }

    static private Consumer<String, String> createE2EConsumer() {
        final Properties props = new Properties()
        props.put(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS)
        props.put(GROUP_ID_CONFIG, 'e2e-test')
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Create the consumer using props.
        final Consumer<String, String> consumer = new KafkaConsumer<>(props)
        consumer.subscribe(['e2e.output.topic'])
        return consumer
    }
}
