package org.rockem.kafcar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HttpKafkaApplication

fun main(args: Array<String>) {
	runApplication<HttpKafkaApplication>(*args)
}

