

package com.fyh.net.exception

import com.fyh.net.exception.HttpResponseException
import okhttp3.Response


/**
 * >= 500 服务器异常
 */
class ServerResponseException(
    response: Response,
    message: String? = null,
    cause: Throwable? = null,
    var tag: Any? = null
) : HttpResponseException(response, message, cause)