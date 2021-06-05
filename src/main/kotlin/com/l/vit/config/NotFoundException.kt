package com.l.vit.config

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "not found exception")
class NotFoundException(s: String) : Exception(s)
