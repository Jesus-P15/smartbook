package co.edu.cecar.smartbooks.screens.auth.components




import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
) {

    var mostrarContrasena by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = value,

        onValueChange = onValueChange,

        label = {
            Text(label)
        },

        modifier = Modifier.fillMaxWidth(),

        singleLine = true,

        visualTransformation =

            if (isPassword && !mostrarContrasena)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,

        keyboardOptions = KeyboardOptions(
            keyboardType =
                if (isPassword)
                    KeyboardType.Password
                else
                    KeyboardType.Text
        ),

        trailingIcon = {

            if (isPassword) {

                IconButton(
                    onClick = {
                        mostrarContrasena = !mostrarContrasena
                    }
                ) {

                    Icon(
                        imageVector =
                            if (mostrarContrasena)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,

                        contentDescription = null
                    )

                }

            }

        }

    )

}