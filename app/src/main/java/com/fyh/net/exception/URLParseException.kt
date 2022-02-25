package com.fyh.net.exception

/**
 * URL地址错误
 */
open class URLParseException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)