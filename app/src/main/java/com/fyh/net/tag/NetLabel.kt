

package com.fyh.net.tag

import com.fyh.net.interfaces.ProgressListener
import java.io.File
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.reflect.KType

sealed class NetLabel {
    class Tags : HashMap<String, Any?>()

    inline class RequestId(val value: Any?)
    inline class RequestGroup(val value: Any?)
    inline class RequestKType(val value: KType?)
    inline class LogRecord(val enabled: Boolean)

    inline class DownloadFileMD5Verify(val enabled: Boolean = true)
    inline class DownloadFileNameDecode(val enabled: Boolean = true)
    inline class DownloadTempFile(val enabled: Boolean = true)
    inline class DownloadFileConflictRename(val enabled: Boolean = true)
    inline class DownloadFileName(val name: String?)
    inline class DownloadFileDir(val dir: String?) {
        constructor(fileDir: File?) : this(fileDir?.absolutePath)
    }

    class UploadListeners : ConcurrentLinkedQueue<ProgressListener>()
    class DownloadListeners : ConcurrentLinkedQueue<ProgressListener>()
}
