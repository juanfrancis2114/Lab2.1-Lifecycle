package com.example.laboratorio1

import android.app.ActivityManager
import android.app.ApplicationStartInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {

    private val tag = "LIFECYCLE_DEBUG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(tag, "onCreate: La Activity se está creando")

        // Paso 6: registrar el tipo de inicio de la aplicación.
        if (Build.VERSION.SDK_INT >= 35) {
            val activityManager =
                getSystemService(ActivityManager::class.java)

            activityManager.addApplicationStartInfoCompletionListener(
                mainExecutor
            ) { startInfo ->

                val tipoInicio = when (startInfo.startType) {
                    ApplicationStartInfo.START_TYPE_COLD ->
                        "FRÍO (Cold Start)"

                    ApplicationStartInfo.START_TYPE_WARM ->
                        "CÁLIDO (Warm Start)"

                    ApplicationStartInfo.START_TYPE_HOT ->
                        "CALIENTE (Hot Start)"

                    else ->
                        "NO DETERMINADO"
                }

                Log.d(
                    "STARTUP_DEBUG",
                    "Tipo de inicio: $tipoInicio"
                )
            }
        } else {
            Log.d(
                "STARTUP_DEBUG",
                "ApplicationStartInfo requiere Android 15 o superior"
            )
        }

        // Obtener el ViewModel con soporte para SavedStateHandle.
        val counterViewModel =
            ViewModelProvider(this)[CounterViewModel::class.java]

        setContent {
            MaterialTheme {
                val count by counterViewModel.count.collectAsState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Contador Seguro: $count",
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Button(
                        onClick = {
                            counterViewModel.increment()
                        }
                    ) {
                        Text(text = "Incrementar")
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart: La Activity es visible")
    }

    override fun onResume() {
        super.onResume()
        Log.d(
            tag,
            "onResume: La Activity tiene el foco (interactuable)"
        )
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause: La Activity pierde el foco")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop: La Activity ya no es visible")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy: La Activity va a ser destruida")
    }
}