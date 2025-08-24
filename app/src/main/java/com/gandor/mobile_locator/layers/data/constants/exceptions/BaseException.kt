package com.gandor.mobile_locator.layers.data.constants.exceptions

data class ServiceExceptionResponseMulti(
    val status: Int,
    val timestamp: String,
    val errors: List<ServiceExceptionResponse>
): Throwable()

data class ServiceExceptionResponse(
    val status: Int,
    val exceptionCode: Int,
    val message: String,
    val timestamp: String
)