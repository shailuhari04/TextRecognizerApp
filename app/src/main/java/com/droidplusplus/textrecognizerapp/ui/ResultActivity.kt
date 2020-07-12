package com.droidplusplus.textrecognizerapp.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.droidplusplus.textrecognizerapp.databinding.ActivityResultBinding
import com.droidplusplus.textrecognizerapp.utils.ClipBoardHelper
import com.droidplusplus.textrecognizerapp.utils.Constants


class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

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
            binding.resultTextTv.text = intent.getStringExtra(Constants.DATA_BUNDLE)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpCopyFunctionality() {
        binding.resultTextTv.setOnLongClickListener {
            clipBoardHelper.copyData(binding.resultTextTv.text.toString())
            true
        }
    }
}
