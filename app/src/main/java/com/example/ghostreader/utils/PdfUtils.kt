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

        if (imageUris.isEmpty()) {
            println("No images provided")
            return null
        }

        try {
            val pdfDocument = PdfDocument()

            var pageNumber = 1

            for (uri in imageUris) {

                val inputStream = context.contentResolver.openInputStream(uri)
                if (inputStream == null) {
                    println("Failed to open image URI")
                    continue
                }

                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()

                if (bitmap == null) {
                    println("Bitmap decode failed")
                    continue
                }

                val pageInfo = PdfDocument.PageInfo.Builder(
                    bitmap.width,
                    bitmap.height,
                    pageNumber++
                ).create()

                val page = pdfDocument.startPage(pageInfo)
                page.canvas.drawBitmap(bitmap, 0f, 0f, null)
                pdfDocument.finishPage(page)
            }

            val pdfFile = FileUtils.createPdfFile(context)

            pdfDocument.writeTo(pdfFile.outputStream())
            pdfDocument.close()

            println("PDF created at: ${pdfFile.absolutePath}")

            return FileUtils.fileToUri(context, pdfFile)

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }



}
