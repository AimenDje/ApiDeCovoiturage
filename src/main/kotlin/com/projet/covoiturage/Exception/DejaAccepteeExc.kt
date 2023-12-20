package com.projet.covoiturage.Exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class DejaAccepteeExc(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)
