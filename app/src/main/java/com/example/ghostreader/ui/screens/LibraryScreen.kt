package com.example.ghostreader.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ghostreader.utils.FileUtils
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryScreen() {

    val context = LocalContext.current

    val documentsDir = File(
        context.getExternalFilesDir(null),
        "Documents"
    )

    var pdfFiles by remember {
        mutableStateOf(
            documentsDir.listFiles()?.toList() ?: emptyList()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "My Library",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(pdfFiles) { file ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .combinedClickable(

                            onClick = {

                                val uri = FileUtils.fileToUri(context, file)

                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    setDataAndType(uri, "application/pdf")
                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                }

                                try {
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            },

                            onLongClick = {
                                file.delete()
                                pdfFiles =
                                    documentsDir.listFiles()?.toList()
                                        ?: emptyList()
                            }
                        )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(text = file.name)

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Size: ${
                                file.length() / 1024
                            } KB"
                        )
                    }
                }
            }
        }
    }
}
