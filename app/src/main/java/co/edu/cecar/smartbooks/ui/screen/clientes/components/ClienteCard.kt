package co.edu.cecar.smartbooks.ui.screen.clientes.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.cecar.smartbooks.data.Constants.DatoCell
import co.edu.cecar.smartbooks.data.DataClass.cliente.ClienteResponse
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional

@Composable
fun ClienteCard(
    cliente: ClienteResponse,
    onEditar: (ClienteResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {

            // ── Encabezado: nombre + identificación ───────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = cliente.nombres,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "ID: ${cliente.identificacion}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            HorizontalDivider(thickness = 0.5.dp)

            // ── Grid: email · celular ─────────────────────────────────────
            Row(modifier = Modifier.fillMaxWidth()) {
                DatoCell(
                    label = "Email",
                    valor = cliente.email,
                    modifier = Modifier.weight(1.5f)
                )
                VerticalDivider(modifier = Modifier.height(56.dp), thickness = 0.5.dp)
                DatoCell(
                    label = "Celular",
                    valor = cliente.celular,
                    modifier = Modifier.weight(1f)
                )
            }

            HorizontalDivider(thickness = 0.5.dp)

            // ── Footer: fecha nacimiento + botón editar ───────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Nac: ${cliente.fechaNacimiento}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                IconButton(
                    onClick = { onEditar(cliente) },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar ${cliente.nombres}",
                        tint = RojoInstitucional,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}