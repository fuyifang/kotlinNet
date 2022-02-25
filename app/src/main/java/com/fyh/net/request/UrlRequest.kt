package com.fyh.net.request

open class UrlRequest : BaseRequest() {

    override fun param(name: String, value: String?) {
        httpUrl.setEncodedQueryParameter(name, value)
    }

    override fun param(name: String, value: String?, encoded: Boolean) {
        httpUrl.setQueryParameter(name, value)
    }

    override fun param(name: String, value: Number?) {
        httpUrl.setQueryParameter(name, value.toString())
    }

    override fun param(name: String, value: Boolean?) {
        httpUrl.setQueryParameter(name, value.toString())
    }
}