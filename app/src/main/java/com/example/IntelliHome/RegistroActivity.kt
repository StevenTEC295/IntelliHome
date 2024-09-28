package com.example.IntelliHome

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        val btn1 = findViewById<Button>(R.id.button)

        btn1.setOnClickListener {

            navegar()

        }
    }

    private fun navegar(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)

    }
}