package test.e2e.org.rockem.kafcar


import spock.lang.Shared
import spock.lang.Specification
import test.e2e.org.rockem.kafcar.support.App
import test.e2e.org.rockem.kafcar.support.KafkaMonitor
import test.e2e.org.rockem.kafcar.support.Message

import java.util.concurrent.TimeUnit

import static org.awaitility.Awaitility.await

class ProduceSpec extends Specification {

    @Shared final App app = new App()
    @Shared final KafkaMonitor kafka = new KafkaMonitor()

    def setupSpec() {
        kafka.start()
        app.startIfNeeded()
    }

    def "produce a message"() {
        given:
        def message = new Message(
            topic: "e2e.output.topic",
            value: "message content")
        when:
        app.produceMessage(message)
        then:
        await().atMost(30, TimeUnit.SECONDS).until { kafka.hasReceived(message) }

    }

    def teardownSpec() {
        kafka.stop()
    }
}
