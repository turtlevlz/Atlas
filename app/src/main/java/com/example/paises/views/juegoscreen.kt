package com.example.paises.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.paises.models.Pais
import java.text.Normalizer

fun normalizar(texto: String): String {
    val separado = Normalizer.normalize(texto.trim().lowercase(), Normalizer.Form.NFD)
    return separado.replace(Regex("\\p{Mn}"), "")
}
fun esCorrecta(respuesta: String, pais: Pais): Boolean {
    val r = normalizar(respuesta)
    return r == normalizar(pais.nombreEs) || r == normalizar(pais.name)
}
fun paisAlAzar(paises: List<Pais>): Pais {
    return paises.filter { it.population > 1_000_000 }.random()
}

@Composable
fun JuegoScreen(paises: List<Pais>) {
    if (paises.isEmpty()) {
        Text("Primero carga la lista de países", modifier = Modifier.padding(24.dp))
        return
    }
    var pais by remember { mutableStateOf(paisAlAzar(paises)) }
    var respuesta by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var aciertos by remember { mutableIntStateOf(0) }
    var intentos by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("¿De qué país es?", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Aciertos: $aciertos de $intentos")
        AsyncImage(
            model = pais.flags.png,
            contentDescription = "Bandera misteriosa",
            modifier = Modifier.fillMaxWidth().aspectRatio(3f / 2f)
        )
        OutlinedTextField(
            value = respuesta,
            onValueChange = { respuesta = it },
            label = { Text("Nombre del país") },
            singleLine = true,
            enabled = mensaje.isEmpty(),
            modifier = Modifier.fillMaxWidth()
        )
        if (mensaje.isEmpty()) {
            Button(onClick = {
                intentos++
                if (esCorrecta(respuesta, pais)) {
                    aciertos++
                    mensaje = "¡Correcto!"
                } else {
                    mensaje = "Era ${pais.nombreEs}"
                }
            }, enabled = respuesta.isNotBlank()) { Text("Revisar") }
        } else {
            Text(text = mensaje, style = MaterialTheme.typography.titleLarge)
            Button(onClick = {
                pais = paisAlAzar(paises)
                respuesta = ""
                mensaje = ""
            }) { Text("Siguiente") }
        }    }
}