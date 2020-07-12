package com.droidplusplus.textrecognizerapp.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.invoke
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.droidplusplus.textrecognizerapp.databinding.ActivityMainBinding
import com.droidplusplus.textrecognizerapp.global.ApiPickerActivityContract
import com.droidplusplus.textrecognizerapp.utils.firebaseVisionImageFromBitmap
import com.droidplusplus.textrecognizerapp.utils.firebaseVisionImageFromFile
import com.droidplusplus.textrecognizerapp.utils.gone
import com.droidplusplus.textrecognizerapp.utils.visible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var isNothingSelected = true

    private var resultText:String? = null

    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews() //view setUp
    }

    private fun setUpViews() {
        binding.btnCamera.setOnClickListener {
            takePictureFromCamera.invoke(imageUri)
        }

        binding.btnGallery.setOnClickListener {
            pickImageFromGallery.launch("image/*")
        }

        binding.btnShowResult.setOnClickListener {
            resultText?.takeIf { it.isNotBlank() }?.let {
                sendResultViaPickApi.invoke(resultText)
            } ?: run {
                btnShowResult.gone()
            }
        }
    }


    private val takePictureFromCamera =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { bitmap ->
            bitmap?.let {
                isNothingSelected = false
                firebaseVisionImageFromBitmap(this, it, sendResultViaPickApi)
                binding.imageIv.setImageBitmap(it)
                binding.tvMessage.gone()
                binding.imageIv.visible()
            }
        }

    private val pickImageFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { uri ->
                isNothingSelected = false
                firebaseVisionImageFromFile(this, uri, sendResultViaPickApi)
                binding.imageIv.setImageURI(uri)
                binding.tvMessage.gone()
                binding.imageIv.visible()
            }
        }

    private val sendResultViaPickApi =
        registerForActivityResult(ApiPickerActivityContract()) { data ->
            takeIf { data == null && isNothingSelected }?.run {
                binding.tvMessage.visible()
                binding.btnShowResult.gone()
            } ?: run {
                resultText = data.toString()
                binding.tvMessage.gone()
                binding.btnShowResult.visible()
            }
        }
}
