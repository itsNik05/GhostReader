package com.example.ghostreader.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

object OcrUtils {

    fun runOcr(context: Context, uri: Uri, onResult: (String) -> Unit) {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            val image = InputImage.fromBitmap(bitmap, 0)

            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

            recognizer.process(image)
                .addOnSuccessListener { result ->
                    onResult(result.text)
                }
                .addOnFailureListener {
                    onResult("")
                }

        } catch (e: Exception) {
            e.printStackTrace()
            onResult("")
        }
    }
}
