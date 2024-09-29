package com.example.intellihome

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.IntelliHome.SocketConnection
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btn1 = findViewById<Button>(R.id.boton_registro_huesped)
        btn1.setOnClickListener {
<<<<<<< HEAD:app/src/main/java/com/example/IntelliHome/LoginActivity.kt
            navegar_huesped()
        }
        val btn_registro_propietario = findViewById<Button>(R.id.boton_registro_propietario)
        btn_registro_propietario.setOnClickListener {
            navegar_propietario()
=======
            val socket =  SocketConnection();
            socket.startConnection();
            navegar()
>>>>>>> SocketConnection:Android/app/src/main/java/com/example/IntelliHome/LoginActivity.kt
        }


    }
<<<<<<< HEAD:app/src/main/java/com/example/IntelliHome/LoginActivity.kt
    private fun navegar_huesped(){
=======
    private fun navegar(){

>>>>>>> SocketConnection:Android/app/src/main/java/com/example/IntelliHome/LoginActivity.kt
        val intent = Intent(this,RegistroActivity::class.java)
        startActivity(intent)
    }

    private fun navegar_propietario(){
        val intent = Intent(this,Registro_propietarioActivity::class.java)
        startActivity(intent)
    }

}
