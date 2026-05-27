package co.edu.cecar.smartbooks.ui.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import co.edu.cecar.smartbooks.R
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(

    titulo: String,

    selectedItem: String,

    navegarADashboard: () -> Unit,

    navegarAClientes: () -> Unit,

    navegarALibros: () -> Unit,

    navegarAVentas: () -> Unit,

    navegarALotes: () -> Unit,

    navegarAInventarios: () -> Unit,

    navegarAUsuarios: () -> Unit,

    navegarACerrarSesion: () -> Unit,


    content: @Composable (PaddingValues) -> Unit

) {

    val drawerState =
        rememberDrawerState(
            DrawerValue.Closed
        )

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet(
                drawerContainerColor =
                    MaterialTheme.colorScheme.surface
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cdi_logo_2022),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(120.dp),
                    contentScale = ContentScale.Fit
                )

                HorizontalDivider()
                Spacer(modifier = Modifier.height(20.dp))


                NavigationDrawerItem(

                    label = {
                        Text("Dashboard")
                    },

                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = null
                        )
                    },

                    selected =
                        selectedItem == "dashboard",

                    colors =
                        NavigationDrawerItemDefaults.colors(

                            selectedContainerColor =
                                RojoInstitucional.copy(alpha = 0.2f),

                            selectedTextColor =
                                RojoInstitucional,

                            selectedIconColor =
                                RojoInstitucional
                        ),

                    onClick = {

                        navegarADashboard()

                        scope.launch {

                            drawerState.close()
                        }
                    }
                )


                NavigationDrawerItem(

                    label = {
                        Text("Clientes")
                    },

                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null
                        )
                    },

                    selected =
                        selectedItem == "clientes",

                    colors =
                        NavigationDrawerItemDefaults.colors(

                            selectedContainerColor =
                                RojoInstitucional.copy(alpha = 0.2f),

                            selectedTextColor =
                                RojoInstitucional,

                            selectedIconColor =
                                RojoInstitucional
                        ),

                    onClick = {

                        navegarAClientes()

                        scope.launch {

                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(

                    label = {
                        Text("Libros")
                    },

                    icon = {
                        Icon(
                            Icons.Default.Book,
                            contentDescription = null
                        )
                    },

                    selected =
                        selectedItem == "libros",

                    colors =
                        NavigationDrawerItemDefaults.colors(

                            selectedContainerColor =
                                RojoInstitucional.copy(alpha = 0.2f),

                            selectedTextColor =
                                RojoInstitucional,

                            selectedIconColor =
                                RojoInstitucional
                        ),

                    onClick = {

                        navegarALibros()

                        scope.launch {

                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(

                    label = {
                        Text("Ventas")
                    },

                    icon = {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = null
                        )
                    },

                    selected =
                        selectedItem == "ventas",

                    colors =
                        NavigationDrawerItemDefaults.colors(

                            selectedContainerColor =
                                RojoInstitucional.copy(alpha = 0.2f),

                            selectedTextColor =
                                RojoInstitucional,

                            selectedIconColor =
                                RojoInstitucional
                        ),


                    onClick = {

                        navegarAVentas()

                        scope.launch {

                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(

                    label = {
                        Text("Lotes")
                    },

                    icon = {
                        Icon(
                            Icons.Default.Inventory,
                            contentDescription = null
                        )
                    },

                    selected =
                        selectedItem == "lotes",

                    colors =
                        NavigationDrawerItemDefaults.colors(

                            selectedContainerColor =
                                RojoInstitucional.copy(alpha = 0.2f),

                            selectedTextColor =
                                RojoInstitucional,

                            selectedIconColor =
                                RojoInstitucional
                        ),


                    onClick = {

                        navegarALotes()

                        scope.launch {

                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(

                    label = {
                        Text("Inventarios")
                    },

                    icon = {
                        Icon(
                            Icons.Default.List,
                            contentDescription = null
                        )
                    },

                    selected =
                        selectedItem == "inventarios",

                    colors =
                        NavigationDrawerItemDefaults.colors(

                            selectedContainerColor =
                                RojoInstitucional.copy(alpha = 0.2f),

                            selectedTextColor =
                                RojoInstitucional,

                            selectedIconColor =
                                RojoInstitucional
                        ),


                    onClick = {

                        navegarAInventarios()

                        scope.launch {

                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(

                    label = {
                        Text("Usuarios")
                    },

                    icon = {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = null
                        )
                    },

                    selected =
                        selectedItem == "usuarios",

                    colors =
                        NavigationDrawerItemDefaults.colors(

                            selectedContainerColor =
                                RojoInstitucional.copy(alpha = 0.2f),

                            selectedTextColor =
                                RojoInstitucional,

                            selectedIconColor =
                                RojoInstitucional
                        ),


                    onClick = {

                        navegarAUsuarios()

                        scope.launch {

                            drawerState.close()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(250.dp))

                HorizontalDivider()

                NavigationDrawerItem(

                    label = {

                        Text("Cerrar sesión")
                    },

                    icon = {

                        Icon(
                            Icons.Default.Logout,
                            contentDescription = null
                        )
                    },

                    selected = false,

                    colors =
                        NavigationDrawerItemDefaults.colors(

                            unselectedTextColor =
                                RojoInstitucional,

                            unselectedIconColor =
                                RojoInstitucional
                        ),

                    onClick = {
                        navegarACerrarSesion()
                    }
                )
            }
        }
    ) {

        Scaffold(

            topBar = {

                TopAppBar(

                    title = {
                        Text(titulo)
                    },

                    navigationIcon = {

                        IconButton(

                            onClick = {

                                scope.launch {

                                    drawerState.open()
                                }
                            }
                        ) {

                            Icon(
                                Icons.Default.Menu,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) { padding ->

            content(padding)
        }
    }
}