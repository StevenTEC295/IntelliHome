package com.example.intellihomeapp
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        evaluate()

    }
    fun evaluate(){
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvErrorMessage = findViewById<TextView>(R.id.tvErrorMessage)

        // Credenciales válidas de ejemplo
        val validUsername = "admin"
        val validPassword = "1234"

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username == validUsername && password == validPassword) {
                // Login exitoso
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            } else {
                // Mostrar error
                tvErrorMessage.visibility = TextView.VISIBLE
            }
        }
    }
}