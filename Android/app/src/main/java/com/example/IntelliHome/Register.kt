package com.example.intellihome

import android.os.Bundle
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.service.autofill.OnClickAction
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnCloseListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

    fun registrar(){
        // Crear una instancia de Gson
        val gson = Gson()

        // Crear un objeto Kotlin
        val persona = Persona("Juan", 25)

        // Convertir el objeto a JSON
        val json = gson.toJson(persona)
        println("Objeto a JSON: $json")

        /*// Convertir el JSON a un objeto Kotlin
        val personaDesdeJson = gson.fromJson(json, Persona::class.java)
        println("JSON a Objeto: ${personaDesdeJson.nombre}, ${personaDesdeJson.edad}")*/
    }

}