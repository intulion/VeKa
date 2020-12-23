package dev.akat.veka.ui.photo

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dev.akat.veka.BuildConfig
import dev.akat.veka.R
import kotlinx.android.synthetic.main.fragment_post_photo.*
import java.io.File
import java.io.FileOutputStream

class PostPhotoFragment : Fragment(R.layout.fragment_post_photo) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext())
            .load(requireArguments().getString(ARG_POST_URL))
            .into(fullPhoto)

        fullPhotoShareButton.setOnClickListener { sharePhoto() }

        fullPhotoSaveButton.setOnClickListener {
            if (haveStoragePermission()) savePhoto() else requestPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            REQUEST_STORAGE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults.first() == PERMISSION_GRANTED) {
                    savePhoto()
                } else {
                    Toast.makeText(context,
                        R.string.label_storage_permission_error,
                        Toast.LENGTH_LONG).show()
                }
                return
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun sharePhoto() {
        shareBitmap(fullPhoto.drawable.toBitmap())
    }

    private fun savePhoto() {
        saveBitmap(fullPhoto.drawable.toBitmap())
        Toast.makeText(context, R.string.label_photo_saved, Toast.LENGTH_SHORT).show()
    }

    private fun saveBitmap(bitmap: Bitmap) {
        val relativeLocation: String = Environment.DIRECTORY_PICTURES
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis().toString())
            put(MediaStore.MediaColumns.MIME_TYPE, MIME_TYPE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }

        val resolver = requireActivity().contentResolver
        val imageUri: Uri = resolver.insert(EXTERNAL_CONTENT_URI, contentValues)!!

        resolver.openOutputStream(imageUri).use { stream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY, stream)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
        }
    }

    private fun shareBitmap(bitmap: Bitmap) {
        val cachePath = File(requireContext().cacheDir, "images").also { it.mkdirs() }
        val newFile = File(cachePath, System.currentTimeMillis().toString())

        FileOutputStream(newFile).use { stream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY, stream)
        }

        val imageUri =
            FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID, newFile)

        val intent = Intent(Intent.ACTION_VIEW).apply {
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            setDataAndType(imageUri, MIME_TYPE)
        }
        startActivity(Intent.createChooser(intent, getString(R.string.label_share_intent)))
    }

    private fun requestPermission() {
        if (!haveStoragePermission()) {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_STORAGE_PERMISSION
            )
        }
    }

    private fun haveStoragePermission(): Boolean =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PERMISSION_GRANTED

    companion object {
        const val TAG = "PostPhotoFragment"

        private const val ARG_POST_URL = "post_url"

        private const val REQUEST_STORAGE_PERMISSION = 42

        private const val COMPRESS_QUALITY = 80
        private const val MIME_TYPE = "image/jpeg"

        fun newInstance(postUrl: String): Fragment {
            return PostPhotoFragment().apply {
                arguments = bundleOf(
                    ARG_POST_URL to postUrl
                )
            }
        }
    }
}
