package com.example.ghostreader.ui.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.ghostreader.data.ScanSession
import com.example.ghostreader.utils.FileUtils
import com.example.ghostreader.utils.OcrUtils
import java.io.File

@Composable
fun CameraScreen() {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    var isProcessing by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {

        AndroidView(
            modifier = Modifier.weight(1f),
            factory = { ctx ->
                val previewView = PreviewView(ctx)

                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val capture = ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build()

                    imageCapture = capture

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA,
                            preview,
                            capture
                        )
                    } catch (e: Exception) {
                        Log.e("CameraScreen", "Camera bind failed", e)
                    }

                }, ContextCompat.getMainExecutor(ctx))

                previewView
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { }) {
                Text("Pages")
            }

            Button(
                enabled = !isProcessing,
                onClick = {
                    val capture = imageCapture ?: return@Button
                    isProcessing = true

                    val photoFile: File = FileUtils.createTempImageFile(context)

                    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                    capture.takePicture(
                        outputOptions,
                        ContextCompat.getMainExecutor(context),
                        object : ImageCapture.OnImageSavedCallback {

                            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                val uri: Uri = FileUtils.fileToUri(context, photoFile)
                                ScanSession.addPage(uri)

                                Toast.makeText(context, "Page Added!", Toast.LENGTH_SHORT).show()

                                OcrUtils.runOcr(context, uri) { text ->
                                    ScanSession.saveOcr(uri, text)
                                }

                                isProcessing = false
                            }

                            override fun onError(exception: ImageCaptureException) {
                                Toast.makeText(context, "Capture failed", Toast.LENGTH_SHORT).show()
                                isProcessing = false
                            }
                        }
                    )
                }
            ) {
                Text(if (isProcessing) "Processing..." else "Capture")
            }
        }
    }
}
