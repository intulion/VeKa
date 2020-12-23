package dev.akat.veka.mappers

import android.content.Context
import android.net.Uri
import java.io.File

class UriFileMapper(val context: Context) : Mapper<Uri, File> {

    override fun map(from: Uri): File {
        val cachePath = File(context.cacheDir, DIR).also { it.mkdirs() }
        val file = File(cachePath, "${System.currentTimeMillis()}.$EXT")

        context.contentResolver.openInputStream(from)?.copyTo(file.outputStream())

        return file
    }

    private companion object {
        const val DIR = "images"
        const val EXT = "jpg"
    }
}
