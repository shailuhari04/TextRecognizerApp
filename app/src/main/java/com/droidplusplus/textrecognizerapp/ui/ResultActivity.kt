package com.droidplusplus.textrecognizerapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.droidplusplus.textrecognizerapp.databinding.ActivityResultBinding
import com.droidplusplus.textrecognizerapp.utils.ClipBoardHelper
import com.droidplusplus.textrecognizerapp.utils.Constants


class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    private var resultText: String = ""

    private val clipBoardHelper: ClipBoardHelper by lazy {
        ClipBoardHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar() //toolbar setUp

        getBundleData() //get the bundle data from intent

        setUpCopyFunctionality()
    }

    private fun setUpToolbar() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getBundleData() {
        intent?.hasExtra(Constants.DATA_BUNDLE)?.takeIf { it }?.let {
            intent.getStringExtra(Constants.DATA_BUNDLE)?.let {
                resultText = it
                binding.resultTextTv.text = resultText
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent()
            intent.putExtra(Constants.DATA_BUNDLE, resultText)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpCopyFunctionality() {
        binding.resultTextTv.setOnLongClickListener {
            clipBoardHelper.copyData(binding.resultTextTv.text.toString())
            true
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(Constants.DATA_BUNDLE, resultText)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
