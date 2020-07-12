package com.droidplusplus.textrecognizerapp.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class ClipBoardHelper(private val context: Context) {

    private val clipboardManager: ClipboardManager by lazy {
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    /**
     * This method will use Clipboard copy functionality
     * @param data
     * @param message with default value
     */
    fun copyData(data: String, message: String = "Copied") {
        val clipData = ClipData.newPlainText("text", data)
        clipboardManager.setPrimaryClip(clipData)
        context.toast(message)
    }
}
