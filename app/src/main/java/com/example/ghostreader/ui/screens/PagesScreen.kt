package com.example.ghostreader.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ghostreader.data.ScanSession
import com.example.ghostreader.utils.PdfUtils

@Composable
fun PagesScreen(onBack: () -> Unit) {

    val context = LocalContext.current
    val pages by ScanSession.pages.collectAsState()
    val ocrTexts by ScanSession.ocrTexts.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onBack) {
                Text("Back")
            }

            Button(
                enabled = pages.isNotEmpty(),
                onClick = {
                    val pdfUri = PdfUtils.createPdf(context, pages)

                    if (pdfUri != null) {
                        Toast.makeText(context, "PDF Created Successfully", Toast.LENGTH_LONG).show()
                        sharePdf(context, pdfUri)
                    } else {
                        Toast.makeText(context, "PDF Failed!", Toast.LENGTH_SHORT).show()
                    }

                }
            ) {
                Text("Export PDF")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (pages.isEmpty()) {
            Text("No pages scanned yet.")
        } else {
            LazyColumn {
                items(pages) { uri ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {

                            AsyncImage(
                                model = uri,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "OCR Text:",
                                style = MaterialTheme.typography.titleSmall
                            )

                            Text(
                                text = ocrTexts[uri] ?: "Processing OCR...",
                                style = MaterialTheme.typography.bodySmall
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(onClick = { ScanSession.removePage(uri) }) {
                                Text("Delete Page")
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun sharePdf(context: Context, pdfUri: Uri) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, pdfUri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(shareIntent, "Share PDF"))
}
