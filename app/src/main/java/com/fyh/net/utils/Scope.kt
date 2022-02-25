

package com.fyh.net.utils

import android.app.Activity
import android.app.Dialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.fyh.net.scope.AndroidScope
import com.fyh.net.scope.DialogCoroutineScope
import com.fyh.net.scope.NetCoroutineScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


/**
 * 作用域内部全在主线程
 * 作用域全部属于异步
 * 作用域内部异常全部被捕获, 不会引起应用崩溃
 */

// <editor-fold desc="加载对话框">

/**
 * 作用域开始时自动显示加载对话框, 结束时自动关闭加载对话框
 * 可以设置全局对话框 [com.drake.net.NetConfig.dialogFactory]
 * @param dialog 仅该作用域使用的对话框
 *
 * 对话框被取消或者界面关闭作用域被取消
 */
fun FragmentActivity.scopeDialog(
    dialog: Dialog? = null,
    cancelable: Boolean = true,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> Unit
) = DialogCoroutineScope(this, dialog, cancelable, dispatcher).launch(block)

fun Fragment.scopeDialog(
    dialog: Dialog? = null,
    cancelable: Boolean = true,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> Unit
) = DialogCoroutineScope(requireActivity(), dialog, cancelable, dispatcher).launch(block)





/**
 * 异步作用域
 *
 * 该作用域生命周期跟随整个应用, 注意内存泄漏
 */
fun scope(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> Unit
): AndroidScope {
    return AndroidScope(dispatcher = dispatcher).launch(block)
}

fun LifecycleOwner.scopeLife(
    lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> Unit
) = AndroidScope(this, lifeEvent, dispatcher).launch(block)

fun Fragment.scopeLife(
    lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_STOP,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> Unit
) = AndroidScope(this, lifeEvent, dispatcher).launch(block)
//</editor-fold>

//<editor-fold desc="网络">

/**
 * 该函数比[scope]多了以下功能
 * - 在作用域内抛出异常时会被回调的[NetConfig.onError]函数中
 * - 自动显示错误信息吐司, 可以通过指定[NetConfig.onError]来取消或者增加自己的处理
 *
 * 该作用域生命周期跟随整个应用, 注意内存泄漏
 */
fun scopeNet(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> Unit
) = NetCoroutineScope(dispatcher = dispatcher).launch(block)

/**
 * 该函数比scopeNet多了自动取消作用域功能
 *
 * 该作用域生命周期跟随LifecycleOwner. 比如传入Activity会默认在[FragmentActivity.onDestroy]时取消网络请求.
 * @receiver 可传入FragmentActivity/AppCompatActivity, 或者其他的实现了LifecycleOwner的类
 * @param lifeEvent 指定LifecycleOwner处于生命周期下取消网络请求/作用域
 * @param dispatcher 调度器, 默认运行在[Dispatchers.Main]即主线程下
 */
fun LifecycleOwner.scopeNetLife(
    lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> Unit
) = NetCoroutineScope(this, lifeEvent, dispatcher).launch(block)

/**
 * 和上述函数功能相同, 只是接受者为Fragment
 *
 * Fragment应当在[Lifecycle.Event.ON_STOP]时就取消作用域, 避免[Fragment.onDestroyView]导致引用空视图
 */
fun Fragment.scopeNetLife(
    lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_STOP,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> Unit
) = NetCoroutineScope(this, lifeEvent, dispatcher).launch(block)




//</editor-fold>