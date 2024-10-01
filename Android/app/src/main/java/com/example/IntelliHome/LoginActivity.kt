package com.example.intellihome

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.IntelliHome.SocketConnection
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupPasswordValidation()
        val btn1 = findViewById<TextView>(R.id.boton_crear_cuenta)
        btn1.setOnClickListener {
            val socket =  SocketConnection();
            socket.startConnection();
            navegar()
        }
    }





    private fun setupPasswordValidation() {
        val passwordField = findViewById<EditText>(R.id.editTextPassword)
        val errorTextView = findViewById<TextView>(R.id.textViewError)

        passwordField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()

                // Verificar si el texto tiene 8 caracteres
                if (password.length == 8) {
                    val hasUppercase = password.count { it.isUpperCase() } >= 1
                    val hasLowercase = password.count { it.isLowerCase() } >= 1
                    val hasDigit = password.count { it.isDigit() } >= 1
                    val hasSpecialChar = password.any { !it.isLetterOrDigit() }

                    // Validar la contraseña
                    if (hasUppercase && hasLowercase && hasDigit && hasSpecialChar) {
                        errorTextView.visibility = View.GONE  // Ocultar el mensaje de error
                        Toast.makeText(this@LoginActivity, "Contraseña válida", Toast.LENGTH_SHORT).show()
                    } else {
                        // Mostrar el error si no cumple con los requisitos
                        errorTextView.text = "Debe contener al menos 1 mayúscula, 1 minúscula, 1 dígito y 1 carácter especial"
                        errorTextView.visibility = View.VISIBLE
                    }
                } else {
                    // Ocultar el mensaje de error si no tiene exactamente 8 caracteres
                    errorTextView.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No necesario
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No necesario
            }
        })
    }









    private fun navegar(){
        val intent = Intent(this,Register::class.java)
        startActivity(intent)
    }



}
