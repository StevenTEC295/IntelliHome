package com.example.intellihome

import android.os.Bundle
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.service.autofill.OnClickAction
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnCloseListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.IntelliHome.SocketConnection
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
// Definimos una data class en Kotlin
data class Persona(val nombre: String, val edad: Int)
class Register : AppCompatActivity() {
    private lateinit var selectDate: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        var selectDate = findViewById<TextInputEditText>(R.id.selectDate)
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
                textMessage("Fecha de nacimiento: $selectedDate")
                selectDate.setText(selectedDate)
            }, cYear, cMonth, cDay)
        calendarDialog.show()
    }
    private fun textMessage(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    fun registrar() {
        val name = findViewById<TextInputEditText>(R.id.name_huesped)
        val apellido = findViewById<TextInputEditText>(R.id.apellidos_huesped)
        val correo = findViewById<TextInputEditText>(R.id.correo_huesped)
        val usuario = findViewById<TextInputEditText>(R.id.usuario_huesped)
        val contraseña1 = findViewById<TextInputEditText>(R.id.contraseña1_huesped)
        val contraseña2 = findViewById<TextInputEditText>(R.id.contraseña2_huesped)
        val hobbies = findViewById<EditText>(R.id.hobbies_huesped)
        val account = findViewById<TextInputEditText>(R.id.Tarjeta_huesped)
        val cvc = findViewById<TextInputEditText>(R.id.CVC_huesped)

        // Valida que los campos no estén vacíos
        if (name.text.isNullOrEmpty() || apellido.text.isNullOrEmpty() || correo.text.isNullOrEmpty() ||
            usuario.text.isNullOrEmpty() || contraseña1.text.isNullOrEmpty() || contraseña2.text.isNullOrEmpty() ||
            hobbies.text.isNullOrEmpty() || account.text.isNullOrEmpty() || cvc.text.isNullOrEmpty()) {
            Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear una instancia de Gson y enviar datos al servidor
        val persona = Persona(
            nombre = name.text.toString(),
            apellido = apellido.text.toString(),
            correo = correo.text.toString(),
            usuario = usuario.text.toString(),
            contraseña1 = contraseña1.text.toString(),
            contraseña2 = contraseña2.text.toString(),
            hobbies = hobbies.text.toString(),
            Account = account.text.toString(),
            cvc = cvc.text.toString()
        )

        val gson = Gson()
        val json = gson.toJson(persona)
        ServerConnection(json)
    }

    /*private fun getData(): MutableMap<String, Editable?> {
        val name = findViewById<TextInputEditText>(R.id.name_huesped)
        val apellido = findViewById<TextInputEditText>(R.id.apellidos_huesped)
        val correo = findViewById<TextInputEditText>(R.id.correo_huesped)
        val usuario = findViewById<TextInputEditText>(R.id.usuario_huesped)
        val contraseña1 = findViewById<TextInputEditText>(R.id.contraseña1_huesped)
        val contraseña2 = findViewById<TextInputEditText>(R.id.contraseña2_huesped)
        val BirthDate = findViewById<TextInputEditText>(R.id.selectDate_huesped)
        val Hobbies = findViewById<EditText>(R.id.hobbies_huesped)
        val Account = findViewById<TextInputEditText>(R.id.Tarjeta_huesped)
        val cvc = findViewById<TextInputEditText>(R.id.CVC_huesped)
        val validoHasta = findViewById<TextInputEditText>(R.id.ValidoHasta_huesped)

        val mutableMap = mutableMapOf(
            "name" to name.text,
            "apellido" to apellido.text,
            "correo" to correo.text,
            "usuario" to usuario.text,
            "contraseña1" to contraseña1.text,
            "contraseña2" to contraseña2.text,
            //"birthdate" to BirthDate.text,
            "hobbies" to Hobbies.text,
            "account" to Account.text,
            "cvc" to cvc.text,
            //"validoHasta" to validoHasta.text
        )
        return mutableMap
    }*/
    fun ServerConnection(json: String) {
        try {
            val connection = SocketConnection()
            connection.startConnection()
            connection.sendMessage(json)
            Toast.makeText(this, "Datos enviados al servidor", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            //Toast.makeText(this,"Datos:${json}", Toast.LENGTH_LONG).show()
            Toast.makeText(this, "Error al enviar los datos: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}