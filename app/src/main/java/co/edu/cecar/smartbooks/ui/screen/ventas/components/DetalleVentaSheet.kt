package co.edu.cecar.smartbooks.ui.screen.ventas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.cecar.smartbooks.data.Constants.CargandoIndicador
import co.edu.cecar.smartbooks.data.Constants.FilaDato
import co.edu.cecar.smartbooks.data.Constants.HandleSheet
import co.edu.cecar.smartbooks.data.Constants.SmartColors
import co.edu.cecar.smartbooks.data.DataClass.venta.VentaResponse
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional


@Composable
fun DetalleVentaSheet(
    venta: VentaResponse,
    cargando: Boolean,
    onCerrar: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable { onCerrar() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = false) {},
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                HandleSheet(modifier = Modifier.align(Alignment.CenterHorizontally))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Detalle de venta", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    IconButton(onClick = onCerrar) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                HorizontalDivider(color = SmartColors.BordeLinea)

                if (cargando) {
                    CargandoIndicador()
                } else {
                    FilaDato("Recibo", venta.numeroRecibo ?: "-")
                    FilaDato("Comprobante", venta.numeroComprobante ?: "-")
                    FilaDato("Cliente", venta.clienteNombre ?: "-")
                    FilaDato("Fecha", venta.fecha ?: "-")

                    // Total destacado
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SmartColors.RojoRelleno)
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Total",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = SmartColors.RojoTexto
                        )
                        Text(
                            "$${"%,.0f".format(venta.total ?: 0.0)}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = RojoInstitucional
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}