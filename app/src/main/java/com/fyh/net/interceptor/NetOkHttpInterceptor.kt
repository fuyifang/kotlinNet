package com.fyh.net.interceptor

import com.fyh.net.exception.HttpFailureException
import com.fyh.net.okhttp.attachToNet
import com.fyh.net.okhttp.detachFromNet
import com.fyh.net.body.toNetRequestBody
import com.fyh.net.body.toNetResponseBody
import com.fyh.net.exception.NetConnectException
import com.fyh.net.exception.NetSocketTimeoutException
import com.fyh.net.exception.NetUnknownHostException
import com.fyh.net.request.downloadListeners
import com.fyh.net.request.uploadListeners
import okhttp3.Interceptor
import okhttp3.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Net代理OkHttp的拦截器
 */
object NetOkHttpInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val netRequestBody = request.body?.toNetRequestBody(request.uploadListeners())
        request = request.newBuilder().method(request.method, netRequestBody).build()
        val response = try {
            chain.call().attachToNet()
            chain.proceed(request)
        } catch (e: SocketTimeoutException) {
            throw NetSocketTimeoutException(request, e.message, e)
        } catch (e: ConnectException) {
            throw NetConnectException(request, cause = e)
        } catch (e: UnknownHostException) {
            throw NetUnknownHostException(request, message = e.message)
        } catch (e: Throwable) {
            throw HttpFailureException(request, cause = e)
        }
        val netResponseBody = response.body?.toNetResponseBody(request.downloadListeners()) {
            chain.call().detachFromNet()
        }
        return response.newBuilder().body(netResponseBody).build()
    }
}