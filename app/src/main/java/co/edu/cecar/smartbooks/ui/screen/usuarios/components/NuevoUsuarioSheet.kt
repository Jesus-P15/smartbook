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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.cecar.smartbooks.data.Constants.SmartColors
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional
import co.edu.cecar.smartbooks.viewmodel.UsuariosViewModel

@Composable
fun NuevoUsuarioSheet(
    viewModel: UsuariosViewModel,
    onCerrar: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val identificacion by viewModel.identificacion.collectAsState()
    val nombres by viewModel.nombres.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val rol by viewModel.rol.collectAsState()

    // Cerrar automáticamente al éxito
    LaunchedEffect(state.usuarioCreadoExito) {
        if (state.usuarioCreadoExito) onCerrar()
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
                    Text("Nuevo Usuario", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    IconButton(onClick = onCerrar) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                HorizontalDivider()

                // Campos
                OutlinedTextField(
                    value = identificacion,
                    onValueChange = viewModel::onIdentificacionChange,
                    label = { Text("Identificación") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = nombres,
                    onValueChange = viewModel::onNombresChange,
                    label = { Text("Nombres completos") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = viewModel::onEmailChange,
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = viewModel::onPasswordChange,
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )

                // Selector de rol
                Text("Rol", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(0 to "Admin", 1 to "Vendedor").forEach { (valor, etiqueta) ->
                        FilterChip(
                            selected = rol == valor,
                            onClick = { viewModel.onRolChange(valor) },
                            label = { Text(etiqueta) }
                        )
                    }
                }

                // Error
                state.errorCrear?.let {
                    Text(it, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                }

                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onCerrar,
                        modifier = Modifier.weight(1f)
                    ) { Text("Cancelar") }

                    Button(
                        onClick = { viewModel.crearUsuario() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = RojoInstitucional),
                        enabled = !state.creandoUsuario
                    ) {
                        if (state.creandoUsuario) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Crear usuario")
                        }
                    }
                }
            }
        }
    }
}