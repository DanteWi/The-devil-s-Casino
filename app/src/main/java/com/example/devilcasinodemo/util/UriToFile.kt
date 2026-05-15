package com.example.devilcasinodemo.utils

import android.content.Context
import android.net.Uri
import java.io.File

fun uriToFile(context: Context, uri: Uri): File {

    val input = context.contentResolver.openInputStream(uri)!!

    val file = File(context.cacheDir, "avatar.jpg")

    file.outputStream().use {
        input.copyTo(it)
    }

    return file
}
