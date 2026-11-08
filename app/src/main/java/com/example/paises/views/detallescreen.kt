package com.example.paises.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.paises.models.Pais

@Composable
fun Dato(etiqueta: String, valor: String) {
    Column {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = valor, style = MaterialTheme.typography.bodyLarge)
    }
}
@Composable
fun DetalleScreen(pais: Pais?, onVolver: () -> Unit) {
    if (pais == null) {
        Text("País no encontrado", modifier = Modifier.padding(24.dp))
        return
    }
    val idiomas = pais.languages?.joinToString { it.name } ?: "Sin datos"
    val monedas = pais.currencies?.joinToString { "${it.name} (${it.symbol})" } ?: "Sin datos"
    val poblacion = "%,d habitantes".format(pais.population)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = pais.flags.png,
            contentDescription = "Bandera de ${pais.nombreEs}",
            modifier = Modifier.fillMaxWidth().aspectRatio(3f / 2f)
        )
        Text(pais.nombreEs, style = MaterialTheme.typography.headlineLarge)
        Text(pais.name, style = MaterialTheme.typography.bodyMedium)
        HorizontalDivider()
        Dato("Capital", pais.capital ?: "Sin capital")
        Dato("Región", pais.region)
        Dato("Población", poblacion)
        Dato("Idiomas", idiomas)
        Dato("Moneda", monedas)
        Button(onClick = onVolver) { Text("Volver") }
    }}