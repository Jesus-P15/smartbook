package co.edu.cecar.smartbooks.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.cecar.smartbooks.R
import co.edu.cecar.smartbooks.screens.auth.components.AuthBackground
import co.edu.cecar.smartbooks.screens.auth.components.AuthCard
import co.edu.cecar.smartbooks.screens.auth.components.AuthTextField
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional
import co.edu.cecar.smartbooks.viewmodel.RestablecerViewModel

@Composable
fun SolicitudRestablecimientoScreen(
    navegarARestablecer: () -> Unit,
    navegarAtras: () -> Unit
) {
    val viewModel: RestablecerViewModel = viewModel()
    val state by viewModel.solicitudState.collectAsState()

    LaunchedEffect(state.exito) {
        if (state.exito) navegarARestablecer()
    }

    AuthBackground {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthCard {
                Image(
                    painter = painterResource(id = R.drawable.cdi_logo_2022),
                    contentDescription = "Logo",
                    modifier = Modifier.height(120.dp).fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    "Recuperar contraseña",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Ingresa tu correo y te enviaremos un código para restablecer tu contraseña.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(20.dp))

                AuthTextField(
                    value = state.email,
                    onValueChange = viewModel::onEmailChange,
                    label = "Correo Electrónico"
                )

                state.error?.let {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = { viewModel.solicitarRestablecimiento() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = RojoInstitucional),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = androidx.compose.ui.graphics.Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Enviar código")
                    }
                }

                Spacer(Modifier.height(12.dp))

                OutlinedButton(
                    onClick = navegarAtras,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver")
                }
            }
        }
    }
}