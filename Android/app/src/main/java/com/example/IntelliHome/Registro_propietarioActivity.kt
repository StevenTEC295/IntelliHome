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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.io.File

class Registro_propietarioActivity : AppCompatActivity() {
    private lateinit var selectDate: TextInputEditText
    private lateinit var imageView: ImageView
    private lateinit var button_subir_foto: Button
    private lateinit var imageUrl: Uri
    private lateinit var btnRegistro: Button
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
        val floatingActionButton_contraseña = findViewById<FloatingActionButton>(R.id.floatingActionButton_contraseña)
        floatingActionButton_contraseña.setOnClickListener {
            val message = getString(R.string.Info_contrasena)
            Snackbar.make(it, message, Snackbar.LENGTH_LONG)
                .setAction("OK") {
                }
                .show()
        }

        val floatingActionButton_confimar_contrasena = findViewById<FloatingActionButton>(R.id.floatingActionButton_confimar_contrasena)
        floatingActionButton_confimar_contrasena.setOnClickListener {
            val message = getString(R.string.Info_contrasena)
            Snackbar.make(it, message, Snackbar.LENGTH_LONG)
                .setAction("OK") {
                }
                .show()
        }

        val etcontrasena_propietario: TextInputEditText = findViewById(R.id.cotra_propietario)
        val etcontrasena_propietario_confirmar: TextInputEditText = findViewById(R.id.confimar_cotra_propietario)
        btnRegistro = findViewById(R.id.button_res)
        btnRegistro.setOnClickListener {
            val contrasena = etcontrasena_propietario.text.toString()
            val contrasena_confirmar = etcontrasena_propietario_confirmar.text.toString()
            if (comprobarContrasena(contrasena)) {
                if (contrasena == contrasena_confirmar ){
                    Toast.makeText(this, getString(R.string.Mensaje_confirmacion_contras), Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, getString(R.string.Mensaje_contras_no_son_iguales), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.Mensaje_contras_no_cumple_con_los_requsitos), Toast.LENGTH_SHORT).show()
            }
            /*val nombre = etNombre.text.toString()
            if (nombre.isNotEmpty()) {
                // Ejecutar la conexión en un hilo separado para no bloquear la interfaz
                thread {
                    sendDataToServer("192.168.0.196", 8080, nombre)
                }
            }*/
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
    private fun comprobarContrasena(contrasena: String): Boolean {
        val patron = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}$")
        return patron.matches(contrasena)
    }
}
