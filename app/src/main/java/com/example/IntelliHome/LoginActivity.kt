package com.example.IntelliHome

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btn1 = findViewById<Button>(R.id.Boton_registro)
        btn1.setOnClickListener {
            navegar()
        }
    }
    private fun navegar(){
        val intent = Intent(this,RegistroActivity::class.java)
        startActivity(intent)

    }
}