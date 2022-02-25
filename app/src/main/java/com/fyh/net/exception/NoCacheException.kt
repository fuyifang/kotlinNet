package com.fyh.net.exception

import com.fyh.net.exception.NetException
import okhttp3.Request

class NoCacheException(
    request: Request,
    message: String? = null,
    cause: Throwable? = null
) : NetException(request, message, cause)