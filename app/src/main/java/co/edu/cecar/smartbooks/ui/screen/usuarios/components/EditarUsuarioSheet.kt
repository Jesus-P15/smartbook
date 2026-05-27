package co.edu.cecar.smartbooks.ui.screen.usuarios.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.cecar.smartbooks.data.Constants.SmartColors
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional
import co.edu.cecar.smartbooks.viewmodel.UsuariosViewModel

@Composable
fun EditarUsuarioSheet(
    viewModel: UsuariosViewModel,
    onCerrar: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val nombres by viewModel.editNombres.collectAsState()
    val email by viewModel.editEmail.collectAsState()
    val rol by viewModel.editRol.collectAsState()
    val activo by viewModel.editActivo.collectAsState()

    LaunchedEffect(state.usuarioEditadoExito) {
        if (state.usuarioEditadoExito) onCerrar()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SmartColors.Superficie)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Editar Usuario", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        state.usuarioAEditar?.let {
                            Text(it.identificacion, fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    IconButton(onClick = onCerrar) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                HorizontalDivider()

                OutlinedTextField(
                    value = nombres,
                    onValueChange = viewModel::onEditNombresChange,
                    label = { Text("Nombres completos") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = viewModel::onEditEmailChange,
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Rol
                Text("Rol", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(0 to "Admin", 1 to "Vendedor").forEach { (valor, etiqueta) ->
                        FilterChip(
                            selected = rol == valor,
                            onClick = { viewModel.onEditRolChange(valor) },
                            label = { Text(etiqueta) }
                        )
                    }
                }

                // Estado activo
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Usuario activo", fontSize = 14.sp)
                    Switch(
                        checked = activo,
                        onCheckedChange = viewModel::onEditActivoChange,
                        colors = SwitchDefaults.colors(checkedThumbColor = RojoInstitucional,
                            checkedTrackColor = RojoInstitucional.copy(alpha = 0.4f))
                    )
                }

                state.errorEditar?.let {
                    Text(it, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(onClick = onCerrar, modifier = Modifier.weight(1f)) {
                        Text("Cancelar")
                    }
                    Button(
                        onClick = { viewModel.actualizarUsuario() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = RojoInstitucional),
                        enabled = !state.actualizandoUsuario
                    ) {
                        if (state.actualizandoUsuario) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp),
                                color = Color.White, strokeWidth = 2.dp)
                        } else {
                            Text("Guardar cambios")
                        }
                    }
                }
            }
        }
    }
}