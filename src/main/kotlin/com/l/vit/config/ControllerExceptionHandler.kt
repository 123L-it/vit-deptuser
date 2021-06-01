package com.l.vit.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class ControllerExceptionHandler {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(ControllerExceptionHandler::class.java)
    }

    init {
        LOGGER.info("init ControllerExceptionHandler")
    }

    @ExceptionHandler(value = [NotFoundException::class])
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun resourceNotFoundException(ex: NotFoundException, request: WebRequest?) = ex.message?.let {
        LOGGER.error("Error code: ${HttpStatus.NOT_FOUND.value()}, message: $it", ex)
        errorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.reasonPhrase,
                it,
                request
        )
    }

    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun unknownException(ex: Exception, request: WebRequest) = ex.message?.let {
        LOGGER.error("Error code: ${HttpStatus.INTERNAL_SERVER_ERROR.value()}, message: $it", ex)
        errorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
                it,
                request
        )
    }

    fun errorResponse(status: Int, error: String, message: String, request: WebRequest?) = linkedMapOf(
            "timestamp" to System.currentTimeMillis(),
            "status" to status,
            "error" to error,
            "message" to message,
            "path" to (request as ServletWebRequest).request.requestURI
    )
}
