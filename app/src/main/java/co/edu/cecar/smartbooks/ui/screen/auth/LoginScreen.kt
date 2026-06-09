package co.edu.cecar.smartbooks.screens


import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.cecar.smartbooks.R
import co.edu.cecar.smartbooks.data.network.SessionManager
import co.edu.cecar.smartbooks.screens.auth.components.AuthBackground
import co.edu.cecar.smartbooks.screens.auth.components.AuthCard
import co.edu.cecar.smartbooks.screens.auth.components.AuthTextField
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional
import co.edu.cecar.smartbooks.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navegarADashboard: () -> Unit,
    navegarAPantallaRestablecerContrasena: () -> Unit
) {
    val viewModel: LoginViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current



    LaunchedEffect(state.loginExitoso) {
        if (state.loginExitoso)
        navegarADashboard()
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
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                )

                Spacer(Modifier.height(30.dp))

                AuthTextField(
                    value = state.email,
                    onValueChange = viewModel::onEmailChange,
                    label = "Correo Electrónico"
                )

                Spacer(Modifier.height(12.dp))

                AuthTextField(
                    value = state.password,
                    onValueChange = viewModel::onPasswordChange,
                    label = "Contraseña",
                    isPassword = true
                )

                // Mensaje de error de la API
                state.error?.let { mensaje ->
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = mensaje,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        viewModel.login { token ->
                            SessionManager.guardarToken(context, token)
                            navegarADashboard()
                        }
                    },
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
                        Text("Iniciar sesión")
                    }
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Olvidé mi contraseña",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable { navegarAPantallaRestablecerContrasena() }
                        .padding(vertical = 8.dp)
                )

                Spacer(Modifier.height(20.dp))

                OutlinedButton(
                    onClick = { (context as? Activity)?.finish() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar")
                }
            }
        }
    }
}