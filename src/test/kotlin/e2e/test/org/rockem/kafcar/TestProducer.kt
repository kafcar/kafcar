package e2e.test.org.rockem.kafcar

import e2e.test.org.rockem.kafcar.support.App
import e2e.test.org.rockem.kafcar.support.KafkaMessagesMonitor
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestProducer {

    private val app: App = App()
    private val kafka: KafkaMessagesMonitor = KafkaMessagesMonitor()

    @BeforeAll
    fun consumeMessages() {
        app.start()
        kafka.start()
    }

    @Test
    fun `produce a message`() {
        val message = Message(
            topic = "kuku.topic",
            key = "key",
            value = "message content")
        // Given
        app.produceMessage(message)
        // Expect
        kafka.hasReceived(message)
    }

    @AfterAll
    fun stopKafka() {
        kafka.stop()
    }
}
