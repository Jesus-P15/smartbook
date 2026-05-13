package co.edu.cecar.smartbooks.screens


import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.edu.cecar.smartbooks.R
import co.edu.cecar.smartbooks.screens.auth.components.AuthBackground
import co.edu.cecar.smartbooks.screens.auth.components.AuthCard
import co.edu.cecar.smartbooks.screens.auth.components.AuthTextField

@Composable
fun LoginScreen(
    navegarADashboard: () -> Unit,
    navegarAPantallaRestablecerContrasena: () -> Unit
) {

    var usuarioState by remember {
        mutableStateOf("")
    }

    var contrasenaState by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

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

                Spacer(modifier = Modifier.height(30.dp))

                AuthTextField(
                    value = usuarioState,

                    onValueChange = {
                        usuarioState = it
                    },

                    label = "Correo Electrónico"
                )

                Spacer(modifier = Modifier.height(12.dp))


                AuthTextField(
                    value = contrasenaState,

                    onValueChange = {
                        contrasenaState = it
                    },

                    label = "Contraseña",

                    isPassword = true
                )

                Spacer(modifier = Modifier.height(20.dp))


                Button(
                    onClick = navegarADashboard,

                    modifier = Modifier.fillMaxWidth(),

                    enabled =
                        usuarioState.isNotBlank() &&
                                contrasenaState.isNotBlank()

                ) {

                    Text("Iniciar Sesión")

                }

                Spacer(modifier = Modifier.height(12.dp))


                Text(
                    text = "Olvidé mi contraseña",

                    textAlign = TextAlign.Center,

                    modifier = Modifier
                        .clickable {
                            navegarAPantallaRestablecerContrasena()
                        }
                        .padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))


                OutlinedButton(
                    onClick = {
                        (context as? Activity)?.finish()
                    },

                    modifier = Modifier.fillMaxWidth()

                ) {

                    Text("Cerrar")

                }

            }

        }

    }

}