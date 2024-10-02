package com.example.intellihome

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject
import java.io.File
import java.io.OutputStream
import java.io.PrintWriter
import java.net.Socket
import java.util.Scanner
import kotlin.concurrent.thread

class Registro_propietarioActivity : AppCompatActivity() {
    private lateinit var selectDate: TextInputEditText
    private lateinit var imageView: ImageView
    private lateinit var button_subir_foto: Button
    private lateinit var imageUrl: Uri
    private lateinit var registerButton: Button

    /*private lateinit var socket: Socket
    private lateinit var out_cliente: PrintWriter
    private lateinit var input_server: Scanner
    private lateinit var outputStream: OutputStream*/


    private lateinit var lastNameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var etusername: TextInputEditText
    private lateinit var accountNumberInput: TextInputEditText
    private lateinit var etvalidunitl: TextInputEditText
    private lateinit var etcvc: TextInputEditText
    private lateinit var addressInput: EditText
    private lateinit var firstNameInput: TextInputEditText



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

        //selectDate = findViewById(R.id.selectDate)
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


        val floatingActionButtonPassword = findViewById<FloatingActionButton>(R.id.floatingActionButton_contraseña)
        floatingActionButtonPassword.setOnClickListener {
            val message = getString(R.string.Info_contrasena)
            Snackbar.make(it, message, Snackbar.LENGTH_LONG)
                .setAction("OK") {
                }
                .show()
        }

        val floatingActionButtonConfirmPassword = findViewById<FloatingActionButton>(R.id.floatingActionButton_confimar_contrasena)
        floatingActionButtonConfirmPassword.setOnClickListener {
            val message = getString(R.string.Info_contrasena)
            Snackbar.make(it, message, Snackbar.LENGTH_LONG)
                .setAction("OK") {
                }
                .show()
        }

        val ownerPassword: TextInputEditText = findViewById(R.id.cotra_propietario)
        val ownerPasswordConfirm: TextInputEditText = findViewById(R.id.confimar_cotra_propietario)

        val phoneInput = findViewById<TextInputEditText>(R.id.phonenumber)
        phoneInput.setText(" +506 ")


        phoneInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().startsWith(" +506 ")) {
                    phoneInput.setText(" +506 ")
                    phoneInput.setSelection(phoneInput.text?.length ?: 0)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })



        registerButton = findViewById(R.id.button_res)
        firstNameInput = findViewById(R.id.etNombre)
        registerButton = findViewById(R.id.button_res)
        emailInput = findViewById(R.id.etCorreo)
        lastNameInput = findViewById(R.id.etApellidos)
        etusername = findViewById(R.id.etusername)
        selectDate = findViewById(R.id.selectDate)
        accountNumberInput = findViewById(R.id.etacountNumber)
        etvalidunitl = findViewById(R.id.etvalidunitl)
        etcvc = findViewById(R.id.etcvc)
        addressInput= findViewById(R.id.Direccion)

        accountNumberInput.setText(" CR ")


        var isUpdatingAccountNumber = false
        accountNumberInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isUpdatingAccountNumber) {
                    if (!s.toString().startsWith(" CR ")) {
                        isUpdatingAccountNumber = true
                        accountNumberInput.setText(" CR ")
                        accountNumberInput.setSelection(accountNumberInput.text?.length ?: 0)
                        isUpdatingAccountNumber = false
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })


        registerButton.setOnClickListener {
            // Obtener los datos de entrada
            val firstName = firstNameInput.text.toString()
            val email = emailInput.text.toString()
            val lastName = lastNameInput.text.toString()
            val username = etusername.text.toString()
            val birthdate = selectDate.text.toString()
            val accountNumberInput = accountNumberInput.text.toString()
            val etvalidunitl = etvalidunitl.text.toString()
            val etcvc = etcvc.text.toString()
            val addressInput = addressInput.text.toString()
            val phoneInput = phoneInput.text.toString()
            // Verificar que ningún campo esté vacío
            val campos = listOf(firstName, email, lastName, username, birthdate, accountNumberInput, etvalidunitl,
                etcvc,addressInput,phoneInput)
            if (campos.any { it.isEmpty() }) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Salir del evento si hay campos vacíos
            }

            // Obtener las contraseñas
            val password = ownerPassword.text.toString()
            val passwordConfirm = ownerPasswordConfirm.text.toString()

            // Verificar la contraseña
            if (!confirmPassword(password)) {
                Toast.makeText(
                    this,
                    getString(R.string.Mensaje_contras_no_cumple_con_los_requsitos),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener // Salir del evento si la contraseña no cumple requisitos
            }

            if (password != passwordConfirm) {
                Toast.makeText(
                    this,
                    getString(R.string.Mensaje_contras_no_son_iguales),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener // Salir del evento si las contraseñas no coinciden
            }

            // Si esta correcto enviar datos al server
            Toast.makeText(
                this,
                getString(R.string.Mensaje_exito_registro),
                Toast.LENGTH_SHORT
            ).show()

            thread {
                val jsonData = createJsonData(
                    firstName,
                    lastName,
                    email,
                    username,
                    password,
                    birthdate,
                    accountNumberInput,
                    etvalidunitl,
                    etcvc,
                    addressInput,
                    phoneInput
                )
                sendDataToServer("192.168.0.196",8080,jsonData)
            }
        }

        /*thread {
            socket = Socket("192.168.0.196", 8080)
            outputStream = socket.getOutputStream()
            out_cliente = PrintWriter(outputStream, true)
            input_server = Scanner(socket.getInputStream())

        }*/
        selectDate.setOnClickListener {
            showDatePickerDialog()
        }

        etvalidunitl.setOnClickListener {
            showDatePickerDialogValidUntil()
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

    private fun showDatePickerDialogValidUntil() {
        val c = Calendar.getInstance()
        val cDay = c.get(Calendar.DAY_OF_MONTH)
        val cMonth = c.get(Calendar.MONTH)
        val cYear = c.get(Calendar.YEAR)

        val calendarDialog = DatePickerDialog(this,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                textMessage(selectedDate)
                etvalidunitl.setText(selectedDate)
            }, cYear, cMonth, cDay
        )
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

    private fun createJsonData(
        firstName: String,
        lastName: String,
        email: String,
        username: String,
        password: String,
        birhtdate: String,
        acountNumber: String,
        validuntil: String,
        cvc: String,
        address: String,
        phone : String
    ): String {
        val json = JSONObject()
        json.put("firstName", firstName)
        json.put("lastName", lastName)
        json.put("email", email)
        json.put("username", username)
        json.put("password", password)
        json.put("birhtdate", birhtdate)
        json.put("acountNumber", acountNumber)
        json.put("validuntil", validuntil)
        json.put("cvc", cvc)
        json.put("address", address)
        json.put("phone",phone)
        return json.toString()
    }


    private fun sendDataToServer(serverIp: String, serverPort: Int,jsonData: String) {
        try {
             val socket = Socket(serverIp, serverPort)
             val outputStream: OutputStream = socket.getOutputStream()
             val printWriter = PrintWriter(outputStream, true)

            printWriter.println(jsonData)
            outputStream.close()
            printWriter.close()
            socket.close()
            println("Se cerro la conexion - envio")
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error al enviar los datos - envio")
        }
    }
    private fun confirmPassword(password: String): Boolean {
        val patron = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}$")
        return patron.matches(password)
    }
}
