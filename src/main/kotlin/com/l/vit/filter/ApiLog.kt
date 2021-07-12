package com.l.vit.filter

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ApiLog : OncePerRequestFilter() {
    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(ApiLog::class.java)
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val uri = request.requestURI
        val queryString = request.queryString
        try {
            LOGGER.info("incoming request uri: $uri, queryString: $queryString")
            chain.doFilter(request, response)
        } catch (e: Exception) {
            LOGGER.error("Error ${e.message}", e)
        } finally {
            val status = response.status
            LOGGER.info("response uri: $uri, queryString: $queryString, statusCode: $status")
        }
    }
}
