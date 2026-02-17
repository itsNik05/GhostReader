package com.example.ghostreader.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfDocument
import android.net.Uri
import java.io.File
import android.provider.MediaStore
import java.io.OutputStream

object PdfUtils {

    fun createPdf(context: Context, imageUris: List<Uri>): Uri? {
        return try {

            val pdfDocument = PdfDocument()

            imageUris.forEachIndexed { index, uri ->
                val inputStream = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                val pageInfo = PdfDocument.PageInfo.Builder(
                    bitmap.width,
                    bitmap.height,
                    index + 1
                ).create()

                val page = pdfDocument.startPage(pageInfo)
                val canvas = page.canvas
                canvas.drawBitmap(bitmap, 0f, 0f, null)
                pdfDocument.finishPage(page)
            }

            // 🔥 USE YOUR OWN DIRECTORY
            val pdfFile = FileUtils.createPdfFile(context)

            pdfDocument.writeTo(pdfFile.outputStream())
            pdfDocument.close()

            // Convert File → Uri for sharing
            FileUtils.fileToUri(context, pdfFile)

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
