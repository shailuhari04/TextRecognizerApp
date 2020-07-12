package com.droidplusplus.textrecognizerapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import java.io.IOException

/**
 * This method will be called if the image is captured using camera.
 * @param context
 * @param imageBitmap
 * @param sendResultViaPickApi
 */
fun firebaseVisionImageFromBitmap(
    context: Context,
    imageBitmap: Bitmap,
    sendResultViaPickApi: ActivityResultLauncher<String?>
) {
    val image = FirebaseVisionImage.fromBitmap(imageBitmap)
    recognizeText(context, image, sendResultViaPickApi)
}

/**
 * This method will be called if the image is selected from internal or external storage.
 * @param context
 * @param uri
 * @param sendResultViaPickApi
 */
fun firebaseVisionImageFromFile(
    context: Context,
    uri: Uri,
    sendResultViaPickApi: ActivityResultLauncher<String?>
) {
    val image: FirebaseVisionImage
    try {
        image = FirebaseVisionImage.fromFilePath(context, uri)
        recognizeText(context, image, sendResultViaPickApi)
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

/**
 * This method is used to extract the text from the image
 * @param image
 * @param context
 * @param sendResultViaPickApi
 */
private fun recognizeText(
    context: Context,
    image: FirebaseVisionImage,
    sendResultViaPickApi: ActivityResultLauncher<String?>
) {
    val detector = FirebaseVision.getInstance()
        .onDeviceTextRecognizer
    detector.processImage(image)
        .addOnSuccessListener { firebaseVisionText ->
            processText(
                context,
                firebaseVisionText,
                sendResultViaPickApi
            )
        }
        .addOnFailureListener { e ->
            e.printStackTrace()
            context.toast("No text found")
        }
}

/**
 * This method is used to process the text and send the result (text) to ResultLayout fragment
 * @param context
 * @param firebaseVisionText
 * @param sendResultViaPickApi
 */
private fun processText(
    context: Context,
    firebaseVisionText: FirebaseVisionText,
    sendResultViaPickApi: ActivityResultLauncher<String?>
) {
    if (firebaseVisionText.textBlocks.isEmpty()) {
        context.toast("No text found or Text may not be clear")
    } else {
        var text = ""
        for (block in firebaseVisionText.textBlocks) {
            text = text + block.text + " "
        }

        sendResultViaPickApi.launch(text)
    }
}