package test.e2e.org.rockem.kafcar.support

import groovy.transform.Immutable

@Immutable
class Message {
    String topic
    String key
    String value
}
