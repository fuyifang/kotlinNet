

package com.fyh.net.exception

import com.fyh.net.exception.HttpResponseException
import okhttp3.Response

/**
 * 400 - 499 客户端请求异常
 */
class RequestParamsException(
    response: Response,
    message: String? = null,
    cause: Throwable? = null,
    var tag: Any? = null
) : HttpResponseException(response, message, cause)