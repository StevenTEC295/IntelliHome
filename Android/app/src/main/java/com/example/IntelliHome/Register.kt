package com.example.intellihome

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.io.OutputStream
import java.net.Socket
import kotlin.concurrent.thread

//El huesped

class RegistroActivity : AppCompatActivity() {
    private lateinit var selectDate: TextInputEditText
    private lateinit var imageView: ImageView
    private lateinit var button_subir_foto: Button
    private lateinit var imageUrl: Uri
    private lateinit var etNombre: TextInputEditText
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
        setContentView(R.layout.activity_registro)

        // Set up dropdown menu for house types
        //val items = listOf("Rustica", "Moderna", "Mansion")
        val items = resources.getStringArray(R.array.house_types).toList()
        val autoComplete: AutoCompleteTextView = findViewById(R.id.autocomplete_text_house)
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, i, _ ->
            val itemSelected = adapterView.getItemAtPosition(i)
            Toast.makeText(this, "Item: $itemSelected", Toast.LENGTH_SHORT).show()
        }

        etNombre = findViewById(R.id.etNombre)
        btnRegistro = findViewById(R.id.button_res)

        // Initialize UI components
        button_subir_foto = findViewById(R.id.button_subir_foto)
        imageView = findViewById(R.id.foto_de_perfil)
        val button_tomar_foto = findViewById<Button>(R.id.button_tomar_foto)
        imageUrl = createImageUri()

//        btnRegistro.setOnClickListener {
//            val nombre = etNombre.text.toString()
//            if (nombre.isNotEmpty()) {
//                // Ejecutar la conexión en un hilo separado para no bloquear la interfaz
//                thread {
//                    sendDataToServer("192.168.0.196", 8080, nombre)
//                }
//            }
//        }
        // Set up click listeners
        button_subir_foto.setOnClickListener {
            pickImageGallery()
        }

        button_tomar_foto.setOnClickListener {
            cameraContract.launch(imageUrl)
        }

        selectDate = findViewById(R.id.selectDate)
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

    // Use the new contract to pick an image from the gallery
    private fun pickImageGallery() {
        galleryContract.launch("image/*")
    }

    private fun createImageUri(): Uri {
        val image = File(filesDir, "camara_photo.png")
        return FileProvider.getUriForFile(this, "com.example.intellihome.FileProvider", image)
    }

    /*private fun sendDataToServer(serverIp: String, serverPort: Int, message: String) {
        try {
            // Crear socket y conectar al servidor
            val socket = Socket(serverIp, serverPort)

            // Obtener el flujo de salida para enviar datos al servidor
            val outputStream: OutputStream = socket.getOutputStream()
            outputStream.write(message.toByteArray())

            // Cerrar el socket una vez que se envían los datos
            socket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/
}
