package com.sample.voicechanger

import android.content.Context
import java.io.File

fun getFileFromAssets(context: Context, fileName: String): File = File(context.cacheDir, fileName)
    .also {
            it.outputStream().use { cache -> context.assets.open(fileName).use { it.copyTo(cache) } }
        }