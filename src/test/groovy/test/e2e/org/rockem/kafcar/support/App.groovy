package test.e2e.org.rockem.kafcar.support

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.apache.http.client.fluent.Request
import org.apache.http.entity.StringEntity
import org.rockem.kafcar.KafcarApplication

import static java.util.concurrent.TimeUnit.SECONDS
import static org.apache.http.entity.ContentType.APPLICATION_JSON
import static org.awaitility.Awaitility.await

class App {

    private final String appUrl = System.getenv('APP_URL') ?: 'http://localhost:8080'
    private boolean started = false

    def startIfNeeded() {
        if (!started) {
            started = true
            KafcarApplication.main()
            waitForAppToBeHealthy()
        }
    }

    def waitForAppToBeHealthy() {
        await().ignoreExceptions().until { getHealthStatus() == 'UP' }
    }

    String getHealthStatus() {
        def health = Request.Get("$appUrl/mgmt/health").execute().returnContent().asBytes()
        return new JsonSlurper().parse(health)['status']
    }

    def produceMessage(Message message) {
        def produceResponse = Request.Post("$appUrl/api/v1/produce")
                .body(new StringEntity(new JsonBuilder(message).toString(), APPLICATION_JSON))
                .execute().returnResponse()
        assert produceResponse.statusLine.statusCode == 200
    }
}
