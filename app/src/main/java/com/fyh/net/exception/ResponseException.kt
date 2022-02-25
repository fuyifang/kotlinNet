


package com.fyh.net.exception

import com.fyh.net.exception.HttpResponseException
import okhttp3.Response

/** 状态码在200..299, 但是返回数据不符合业务要求可以抛出该异常 */
class ResponseException(
    response: Response,
    message: String? = null,
    cause: Throwable? = null,
    var tag: Any? = null
) : HttpResponseException(response, message, cause)