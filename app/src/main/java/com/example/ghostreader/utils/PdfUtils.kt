package com.example.ghostreader.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfDocument
import android.net.Uri
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

            val fileName = "Scan_${System.currentTimeMillis()}.pdf"

            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Documents/GhostReader")
            }

            val uri = context.contentResolver.insert(
                MediaStore.Files.getContentUri("external"),
                values
            ) ?: return null

            val outputStream: OutputStream? = context.contentResolver.openOutputStream(uri)

            outputStream?.use {
                pdfDocument.writeTo(it)
            }

            pdfDocument.close()

            uri

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
