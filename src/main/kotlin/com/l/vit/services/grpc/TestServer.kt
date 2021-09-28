package com.l.vit.services.grpc

import io.grpc.ServerBuilder

class TestServer(serverBuilder: ServerBuilder<*>, private val port: Int) {
    constructor(port: Int) : this(ServerBuilder.forPort(port), port)
    private val server = serverBuilder
            .addService(TestService())
            .build()

    fun start() {
        server.start()
        println("Server started on port $port")
        Runtime.getRuntime().addShutdownHook(Thread {
            stop()
        })
    }

    private fun stop() = server?.shutdown()

    fun blockUntilShutdown() = server?.awaitTermination()

}

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 50051
    val server = TestServer(port)
    server.start()
    server.blockUntilShutdown()
}
