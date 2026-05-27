package co.edu.cecar.smartbooks.ui.screen.libros.componentsLibros


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CampoTexto(

    label: String,

    value: String,

    onValueChange: (String) -> Unit,

    modifier: Modifier = Modifier,

    placeholder: String = "",

    keyboardType: KeyboardType = KeyboardType.Text,

    errorMessage: String? = null,

    prefix: String? = null

) {

    OutlinedTextField(

        value = value,

        onValueChange = onValueChange,

        modifier = modifier.fillMaxWidth(),

        label = {
            Text(label)
        },

        placeholder = {

            if (placeholder.isNotBlank()) {

                Text(placeholder)
            }
        },

        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),

        isError = errorMessage != null,

        supportingText = {

            errorMessage?.let {

                Text(it)
            }
        },

        prefix = {

            prefix?.let {

                Text(it)
            }
        },

        singleLine = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoDropdown(

    label: String,

    opciones: List<String>,

    seleccionado: String,

    onSeleccionar: (String) -> Unit,

    errorMessage: String? = null

) {

    var expanded by remember {

        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(

        expanded = expanded,

        onExpandedChange = {

            expanded = !expanded
        }
    ) {

        OutlinedTextField(

            value = seleccionado,

            onValueChange = {},

            readOnly = true,

            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),

            label = {

                Text(label)
            },

            trailingIcon = {

                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            },

            isError = errorMessage != null,

            supportingText = {

                errorMessage?.let {

                    Text(it)
                }
            }
        )

        ExposedDropdownMenu(

            expanded = expanded,

            onDismissRequest = {

                expanded = false
            }
        ) {

            opciones.forEach { opcion ->

                DropdownMenuItem(

                    text = {

                        Text(opcion)
                    },

                    onClick = {

                        onSeleccionar(opcion)

                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SeccionFormulario(

    titulo: String,

    contenido: @Composable () -> Unit

) {

    Card(

        modifier = Modifier.fillMaxWidth()

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)

        ) {

            Text(

                text = titulo,

                style =
                    MaterialTheme.typography.titleMedium
            )

            contenido()
        }
    }
}

@Composable
fun StockChip(

    stock: Int

) {

    AssistChip(

        onClick = {},

        label = {

            Text("Stock: $stock")
        }
    )
}


@Composable
fun InfoChip(

    texto: String

) {

    AssistChip(

        onClick = {},

        label = {

            Text(texto)
        }
    )
}