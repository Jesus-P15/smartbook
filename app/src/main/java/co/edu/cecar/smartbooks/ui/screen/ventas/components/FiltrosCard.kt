package co.edu.cecar.smartbooks.ui.screen.ventas.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.cecar.smartbooks.data.Constants.SeccionCard
import co.edu.cecar.smartbooks.data.Constants.SmartColors


@Composable
fun FiltrosCard(
    fechaDesde: String,
    fechaHasta: String,
    busqueda: String,
    onFechaDesdeChange: (String) -> Unit,
    onFechaHastaChange: (String) -> Unit,
    onBusquedaChange: (String) -> Unit,
    onLimpiar: () -> Unit
) {
    SeccionCard {
        Text(
            "FILTROS",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = fechaDesde,
                onValueChange = onFechaDesdeChange,
                label = { Text("Desde", fontSize = 12.sp) },
                placeholder = { Text("aaaa-mm-dd", fontSize = 11.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SmartColors.AzulPrincipal,
                    unfocusedBorderColor = SmartColors.BordeLinea
                )
            )
            OutlinedTextField(
                value = fechaHasta,
                onValueChange = onFechaHastaChange,
                label = { Text("Hasta", fontSize = 12.sp) },
                placeholder = { Text("aaaa-mm-dd", fontSize = 11.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SmartColors.AzulPrincipal,
                    unfocusedBorderColor = SmartColors.BordeLinea
                )
            )
        }

        OutlinedTextField(
            value = busqueda,
            onValueChange = onBusquedaChange,
            label = { Text("Buscar por cliente o recibo", fontSize = 12.sp) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SmartColors.AzulPrincipal,
                unfocusedBorderColor = SmartColors.BordeLinea
            )
        )

        OutlinedButton(
            onClick = onLimpiar,
            modifier = Modifier.align(Alignment.End),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = SmartColors.RojoTexto),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = androidx.compose.ui.graphics.SolidColor(SmartColors.RojoTexto.copy(alpha = 0.4f))
            )
        ) {
            Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(Modifier.width(4.dp))
            Text("Limpiar filtros", fontSize = 13.sp)
        }
    }
}