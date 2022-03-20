package e2e.test.org.rockem.kafcar

data class Message(
    val topic: String,
    val key: String,
    val value: String)
