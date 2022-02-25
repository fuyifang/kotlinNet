
package com.fyh.net.interfaces

import com.fyh.net.component.Progress

/**
 * 进度监听器, 为下载和上传两者
 *
 * @param interval 进度监听器刷新的间隔时间, 单位为毫秒, 默认值为500ms
 */
abstract class ProgressListener(var interval: Long = 500) {
    // 上次触发监听器时的开机时间
    var elapsedTime = 0L

    // 距离上次触发监听器的间隔字节数
    var intervalByteCount = 0L

    /**
     * 监听上传/下载进度回调函数
     * @param p 上传或者下载进度
     */
    abstract fun onProgress(p: Progress)
}