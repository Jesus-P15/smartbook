package co.edu.cecar.smartbooks.screens.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import co.edu.cecar.smartbooks.R
import co.edu.cecar.smartbooks.screens.auth.components.AuthBackground
import co.edu.cecar.smartbooks.screens.auth.components.AuthCard
import co.edu.cecar.smartbooks.screens.auth.components.AuthTextField

@Composable
fun RestablecerContrasenaScreen(
    navegarALogin: () -> Unit
) {

    var correoState by remember {
        mutableStateOf("")
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

                Spacer(modifier = Modifier.height(30.dp))


                Text(
                    text = "Restablecer Contraseña"
                )

                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = "Ingresa tu correo electrónico para enviarte las instrucciones de recuperación."
                )

                Spacer(modifier = Modifier.height(20.dp))


                AuthTextField(
                    value = correoState,

                    onValueChange = {
                        correoState = it
                    },

                    label = "Correo Electrónico"
                )

                Spacer(modifier = Modifier.height(20.dp))


                Button(
                    onClick = {

                    },

                    modifier = Modifier.fillMaxWidth(),

                    enabled = correoState.isNotBlank()

                ) {

                    Text("Enviar")

                }

                Spacer(modifier = Modifier.height(12.dp))


                OutlinedButton(
                    onClick = navegarALogin,

                    modifier = Modifier.fillMaxWidth()

                ) {

                    Text("Volver")

                }

            }

        }

    }

}