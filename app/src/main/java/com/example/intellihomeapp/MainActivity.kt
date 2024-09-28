package com.example.intellihomeapp
import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ContentView
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // Importa las funciones de layout
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.* // Importa los elementos materiales
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.* // Asegúrate de importar Locale
import androidx.compose.ui.unit.sp
import com.example.intellihomeapp.R
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import android.os.LocaleList




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()

        }
    }
}

@Composable
fun LoginScreen() {
    var isSpanish by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Campo de email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {Text(stringResource(R.string.Email)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.Password))  },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) {
                    painterResource(id = R.drawable.openeye)
                } else {
                    painterResource(id = R.drawable.closeeye)
                }
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Image(
                        painter = icon,
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                        modifier = Modifier.size(24.dp) // Ajusta el tamaño si es necesario
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de login
        Button(
            onClick = {
                // Lógica de login aquí
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.Login))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                setLocale(context, "en")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("English")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                setLocale(context, "es-rCR")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Español")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {

    LoginScreen()
}


fun setLocale(context: Context, localeString: String) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java)
            .applicationLocales = LocaleList.forLanguageTags(localeString)
    }
    else{
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(localeString))
    }
}



