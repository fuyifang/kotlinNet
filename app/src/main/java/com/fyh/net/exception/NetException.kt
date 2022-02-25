

package com.fyh.net.exception

import okhttp3.Request
import java.io.IOException


/**
 * Net网络异常
 * @param message 异常信息
 */
open class NetException(
    open val request: Request,
    message: String? = null,
    cause: Throwable? = null
) : IOException(message, cause) {

    override fun getLocalizedMessage(): String? {
        return if (message != null) "$message (${request.url})" else "(${request.url})"
    }
}