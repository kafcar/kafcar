package test.e2e.org.rockem.kafcar

import spock.lang.Shared
import spock.lang.Specification
import test.e2e.org.rockem.kafcar.support.App
import test.e2e.org.rockem.kafcar.support.KafkaMonitor
import test.e2e.org.rockem.kafcar.support.Message

class ProduceSpec extends Specification {

    @Shared final App app = new App()
    @Shared final KafkaMonitor kafka = new KafkaMonitor()

    def setupSpec() {
        kafka.start()
        app.start()
    }

    def "produce a message"() {
        given:
        def message = new Message(
            topic: "kafcar.output.topic",
            key: "kuku",
            value: "message content")
        when:
        app.produceMessage(message)
        then:
        kafka.hasReceived(message)
    }

    def teardownSpec() {
        kafka.stop()
    }
}
