package test.e2e.org.rockem.kafcar.support

import org.awaitility.Awaitility
import org.rockem.kafcar.KafcarApplication

import static org.awaitility.Awaitility.await

class App {

    private final String appUrl = System.getenv('APP_URL')
    private boolean started = false

    def startIfNeeded() {
        if(!started) {
            KafcarApplication.main()
            waitForAppToBeHealthy()
        }
    }

    def waitForAppToBeHealthy() {
        await().until { }
    }

    def produceMessage(Message message) {

    }
}
