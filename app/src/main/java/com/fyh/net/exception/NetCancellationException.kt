package com.fyh.net.exception

import com.fyh.net.Net
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.CancellationException

/**
 * 取消网络任务的异常
 */
class NetCancellationException(
    coroutineScope: CoroutineScope,
    message: String? = null,
) : CancellationException(message) {
    init {
        Net.cancelGroup(coroutineScope.coroutineContext[CoroutineExceptionHandler])
    }
}


/**
 * 抛出该异常将取消作用域内所有的网络请求
 */
@Suppress("FunctionName")
fun CoroutineScope.NetCancellationException(message: String? = null): NetCancellationException {
    return NetCancellationException(this, message)
}