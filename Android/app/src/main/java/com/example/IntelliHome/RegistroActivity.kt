package com.example.intellihome

import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnCloseListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
// Definimos una data class en Kotlin
data class Persona(val nombre: String, val edad: Int)
class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
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