package com.droidplusplus.textrecognizerapp.global

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.droidplusplus.textrecognizerapp.ui.ResultActivity
import com.droidplusplus.textrecognizerapp.utils.Constants

class ApiPickerActivityContract : ActivityResultContract<String?, String?>() {
    override fun createIntent(context: Context, input: String?): Intent {
        val intent = Intent(context, ResultActivity::class.java)
        intent.putExtra(Constants.DATA_BUNDLE, input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?) = when {
        resultCode != Activity.RESULT_OK -> null
        else -> intent?.getStringExtra(Constants.DATA_BUNDLE)
    }
}