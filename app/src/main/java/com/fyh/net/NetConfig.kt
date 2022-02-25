

package com.fyh.net

import android.annotation.SuppressLint
import android.app.Application
import android.app.Dialog
import android.app.ProgressDialog
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.fyh.net.NetConfig.app
import com.fyh.net.NetConfig.converter
import com.fyh.net.NetConfig.dialogFactory
import com.fyh.net.NetConfig.errorHandler
import com.fyh.net.NetConfig.host
import com.fyh.net.NetConfig.logEnabled
import com.fyh.net.NetConfig.requestInterceptor
import com.fyh.net.NetConfig.runningCalls
import com.fyh.net.convert.NetConverter
import com.fyh.net.interceptor.RequestInterceptor
import com.fyh.net.interfaces.NetDialogFactory
import com.fyh.net.interfaces.NetErrorHandler
import com.fyh.net.okhttp.toNetOkhttp
import okhttp3.Call
import okhttp3.OkHttpClient
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentLinkedQueue


/**
 * Net的全局配置
 *
 * @property app 全局上下文, 一般执行[NetConfig.init]即可, 无需手动赋值
 * @property host 全局的域名或者ip(baseUrl)
 * @property runningCalls Net中正在运行的请求Call
 * @property requestInterceptor 请求拦截器
 * @property logEnabled 是否启用日志
 * @property dialogFactory 全局加载框
 * @property errorHandler 全局错误处理器, 会覆盖onError/onStateError
 * @property converter 全局数据转换器
 */
@SuppressLint("StaticFieldLeak")
object NetConfig {

    lateinit var app: Application

    /** 全局域名 */
    var host: String = ""

    /** 全局单例请求客户端 */
    var okHttpClient: OkHttpClient = OkHttpClient.Builder().toNetOkhttp().build()
        set(value) {
            field = value.toNetOkhttp()
        }

    /** 是否启用日志 */
    var logEnabled = true

    /** 网络异常日志的标签 */
    var logTag = "NET-LOG"

    /** 运行中的请求 */
    var runningCalls: ConcurrentLinkedQueue<WeakReference<Call>> = ConcurrentLinkedQueue()
        private set

    /** 请求拦截器 */
    var requestInterceptor: RequestInterceptor? = null

    /** 响应数据转换器 */
    var converter: NetConverter = NetConverter

    /** 错误处理器 */
    var errorHandler: NetErrorHandler = NetErrorHandler

    /** 对话框构建工厂 */
    var dialogFactory: NetDialogFactory = NetDialogFactory

    /** 对话框, 已废弃, 请使用[dialogFactory] */
    @Deprecated("废弃", replaceWith = ReplaceWith("NetConfig.dialogFactory"), DeprecationLevel.ERROR)
    var onDialog: (FragmentActivity) -> Dialog = { activity ->
        val progress = ProgressDialog(activity)
        progress.setMessage(activity.getString(R.string.net_dialog_msg))
        progress
    }

    /** 全局错误处理器, 已废弃, 请使用[errorHandler] */
    @Deprecated("使用NetErrorHandler统一处理错误", replaceWith = ReplaceWith("NetConfig.errorHandler"), DeprecationLevel.ERROR)
    var onError: Throwable.() -> Unit = onError@{
    }


    /** 网络请求自动处理缺省页时发生错误的处理逻辑 */
    @Deprecated("使用NetErrorHandler统一处理错误", replaceWith = ReplaceWith("NetConfig.errorHandler"), DeprecationLevel.ERROR)
    var onStateError: Throwable.(view: View) -> Unit = {
    }

    //<editor-fold desc="初始化">
    /**
     * 初始化框架, 该函数仅在Kotlin下有效
     *
     * @param host 请求url的主机名
     * @param config 进行配置网络请求
     */
    fun init(host: String = "", config: OkHttpClient.Builder.() -> Unit = {}) {
        NetConfig.host = host
        val builder = OkHttpClient.Builder()
        builder.config()
        okHttpClient = builder.toNetOkhttp().build()
    }

    /**
     * 初始化框架, 该函数仅在Kotlin下有效
     *
     * @param host 请求url的主机名
     * @param config 进行配置网络请求
     */
    fun init(host: String = "", config: OkHttpClient.Builder) {
        NetConfig.host = host
        okHttpClient = config.toNetOkhttp().build()
    }
    //</editor-fold>
}

