package com.example.ghostreader.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {

    fun createTempImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val dir = File(context.cacheDir, "scans")

        if (!dir.exists()) dir.mkdirs()

        return File(dir, "SCAN_$timeStamp.jpg")
    }

    fun fileToUri(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            context.packageName + ".provider",
            file
        )
    }
    fun createPdfFile(context: Context): File {

        val documentsDir = File(
            context.getExternalFilesDir(null),
            "Documents"
        )

        if (!documentsDir.exists()) {
            documentsDir.mkdirs()
        }

        val fileName = "Scan_${System.currentTimeMillis()}.pdf"

        return File(documentsDir, fileName)
    }


}
