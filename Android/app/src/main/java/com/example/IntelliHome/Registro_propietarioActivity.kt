package com.example.intellihome

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.textfield.TextInputEditText
import java.io.File

class Registro_propietarioActivity : AppCompatActivity() {
    private lateinit var selectDate: TextInputEditText
    private lateinit var imageView: ImageView
    private lateinit var button_subir_foto: Button
    private lateinit var imageUrl: Uri

    // Register for camera activity result
    private val cameraContract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        imageView.setImageURI(imageUrl)
    }

    // Register for gallery activity result
    private val galleryContract = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageView.setImageURI(it)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_propietario)

        selectDate = findViewById(R.id.selectDate)
        button_subir_foto = findViewById(R.id.button_subir_foto_propietario)
        imageView = findViewById(R.id.foto_de_perfil_propietario)
        val button_tomar_foto = findViewById<Button>(R.id.button_tomar_foto_propietario)
        imageUrl = createImageUri()

        // Set up click listeners
        button_subir_foto.setOnClickListener {
            pickImageGallery()
        }

        button_tomar_foto.setOnClickListener {
            cameraContract.launch(imageUrl)
        }

        selectDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val c = Calendar.getInstance()
        val cDay = c.get(Calendar.DAY_OF_MONTH)
        val cMonth = c.get(Calendar.MONTH)
        val cYear = c.get(Calendar.YEAR)

        val calendarDialog = DatePickerDialog(this,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                textMessage(selectedDate)
                selectDate.setText(selectedDate)
            }, cYear, cMonth, cDay)
        calendarDialog.show()
    }

    private fun textMessage(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    // New method to pick an image from the gallery using the new API
    private fun pickImageGallery() {
        galleryContract.launch("image/*")
    }

    private fun createImageUri(): Uri {
        val image = File(filesDir, "camara_photo.png")
        return FileProvider.getUriForFile(this, "com.example.intellihome.FileProvider", image)
    }
}
