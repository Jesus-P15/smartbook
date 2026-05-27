package co.edu.cecar.smartbooks.ui.screen.usuarios.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.cecar.smartbooks.data.DataClass.usuario.UsuarioResponse
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional

@Composable
fun UsuarioCard(
    usuario: UsuarioResponse,
    onEditar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFE0E0E0))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            // Fila superior: nombre + badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = usuario.nombres,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = usuario.identificacion,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    RolChip(rol = usuario.rol)
                    EstadoChip(activo = usuario.activo)
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = Color(0xFFEEEEEE)
            )

            // Fila inferior: email + botón editar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = usuario.email,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                TextButton(
                    onClick = onEditar,
                    contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = RojoInstitucional,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "Editar",
                        color = RojoInstitucional,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}



@Composable
private fun RolChip(rol: String) {
    val esAdmin = rol.equals("Admin", ignoreCase = true)
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (esAdmin) Color(0xFFFFE5E5) else Color(0xFFE8F4FD)
    ) {
        Text(
            text = rol,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = if (esAdmin) Color(0xFFCC3333) else Color(0xFF3366CC)
        )
    }
}

@Composable
private fun EstadoChip(activo: Boolean) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (activo) Color(0xFFE6F9F0) else Color(0xFFFFF0F0)
    ) {
        Text(
            text = if (activo) "Activo" else "Inactivo",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = if (activo) Color(0xFF1A8A4A) else Color(0xFFCC3333)
        )
    }
}