
package com.fyh.net.exception

import okhttp3.Response


/**
 * 转换数据异常
 */
class ConvertException(
    response: Response,
    message: String? = null,
    cause: Throwable? = null,
    var tag: Any? = null
) : HttpResponseException(response, message, cause)