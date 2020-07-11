package com.droidplusplus.textrecognizerapp.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.droidplusplus.textrecognizerapp.databinding.ActivityResultBinding
import com.droidplusplus.textrecognizerapp.utils.Constants
import com.droidplusplus.textrecognizerapp.utils.toast


class ResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityResultBinding

    private val clipboardManager: ClipboardManager by lazy {
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
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
            binding.resultText.text = intent.getStringExtra(Constants.DATA_BUNDLE)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpCopyFunctionality() {
        binding.resultText.setOnLongClickListener {
            val clipData = ClipData.newPlainText("text", binding.resultText.text)
            clipboardManager.setPrimaryClip(clipData)
            toast("All Text Copied.")

            true
        }
    }
}
