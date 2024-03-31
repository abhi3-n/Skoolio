package com.project.skoolio.screens.TestScreen

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.project.skoolio.R
import com.project.skoolio.components.CustomTextField
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class ComposeFileProvider : FileProvider(
    R.xml.filepaths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            // 1
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            // 2
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory
            )
            // 3
            val authority = context.packageName + ".provider"
            // 4
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}


@Composable
fun TestScreen(){
    ImagePicker()
}


@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var hasImage by remember {
        mutableStateOf(false)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            Toast.makeText(context,"$success", Toast.LENGTH_SHORT).show()
            hasImage = success
        }
    )

    Box(
        modifier = modifier,
    ) {
        if (hasImage && imageUri != null
//            && uriToByteArray(context, imageUri!!).isNotEmpty()
            ) {
            AsyncImage(
                model = imageUri,
                modifier = Modifier.fillMaxWidth(),
                contentDescription = "Selected image",
            )
//            val byteArray = uriToByteArray(context, imageUri!!)
//            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
//            val imageBitmap = bitmap.asImageBitmap()
//            Image(bitmap = imageBitmap, contentDescription = "Image from ByteArray")
//            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    imagePicker.launch("image/*")
                },
            ) {
                Text(
                    text = "Select Image"
                )
            }
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = {
                    val uri = ComposeFileProvider.getImageUri(context)
                    imageUri = uri
                    hasImage = false
                    cameraLauncher.launch(uri)
                    Toast.makeText(context,"$imageUri", Toast.LENGTH_SHORT).show()
                },
            ) {
                Text(
                    text = "Take photo"
                )
            }
        }
    }
}


fun uriToByteArray(context: Context, uri: Uri): ByteArray {
    val contentResolver: ContentResolver = context.contentResolver
    var inputStream: InputStream? = null
    var byteArrayOutputStream: ByteArrayOutputStream? = null

        inputStream = contentResolver.openInputStream(uri)
        byteArrayOutputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var bytesRead: Int

        while (inputStream?.read(buffer).also { bytesRead = it!! } != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead)
        }

        return byteArrayOutputStream.toByteArray()
        inputStream?.close()
        byteArrayOutputStream?.close()
}