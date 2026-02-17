package com.example.ghostreader.data

import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ScanSession {

    private val _pages = MutableStateFlow<List<Uri>>(emptyList())
    val pages: StateFlow<List<Uri>> = _pages

    private val _ocrTexts = MutableStateFlow<Map<Uri, String>>(emptyMap())
    val ocrTexts: StateFlow<Map<Uri, String>> = _ocrTexts

    fun addPage(uri: Uri) {
        _pages.value = _pages.value + uri
    }

    fun removePage(uri: Uri) {
        _pages.value = _pages.value.filterNot { it == uri }
        _ocrTexts.value = _ocrTexts.value - uri
    }

    fun saveOcr(uri: Uri, text: String) {
        _ocrTexts.value = _ocrTexts.value + (uri to text)
    }

    fun clearAll() {
        _pages.value = emptyList()
        _ocrTexts.value = emptyMap()
    }
}
