package co.edu.cecar.smartbooks.ui.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import co.edu.cecar.smartbooks.R
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional
import co.edu.cecar.smartbooks.data.network.SessionManager
private data class BottomNavItem(
    val ruta: String,
    val label: String,
    val icono: ImageVector,
    val accion: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    titulo: String,
    selectedItem: String,
    navegarAPerfil: () -> Unit,
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
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val esAdmin = remember { SessionManager.obtenerRol().equals("Admin", ignoreCase = true) }  // ← añadido

    val bottomItems = listOf(
        BottomNavItem("dashboard", "Inicio",   Icons.Default.Home,          navegarADashboard),
        BottomNavItem("clientes",  "Clientes", Icons.Default.Person,        navegarAClientes),
        BottomNavItem("libros",    "Libros",   Icons.Default.Book,          navegarALibros),
        BottomNavItem("ventas",    "Ventas",   Icons.Default.ShoppingCart,  navegarAVentas),
        BottomNavItem("perfil",    "Perfil",   Icons.Default.AccountCircle, navegarAPerfil),
    )

    @Composable
    fun DrawerItem(
        label: String,
        icono: ImageVector,
        ruta: String,
        onClick: () -> Unit
    ) {
        NavigationDrawerItem(
            label = { Text(label) },
            icon = { Icon(icono, contentDescription = null) },
            selected = selectedItem == ruta,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = RojoInstitucional.copy(alpha = 0.2f),
                selectedTextColor = RojoInstitucional,
                selectedIconColor = RojoInstitucional
            ),
            onClick = {
                onClick()
                scope.launch { drawerState.close() }
            }
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface
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

                DrawerItem("Lotes",       Icons.Default.Inventory,     "lotes",       navegarALotes)
                DrawerItem("Inventarios", Icons.Default.List,          "inventarios", navegarAInventarios)
                if (esAdmin) {
                    DrawerItem("Usuarios", Icons.Default.ManageAccounts, "usuarios", navegarAUsuarios)
                }

                Spacer(modifier = Modifier.weight(1f))

                HorizontalDivider()

                NavigationDrawerItem(
                    label = { Text("Cerrar sesión") },
                    icon = { Icon(Icons.Default.Logout, contentDescription = null) },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = RojoInstitucional,
                        unselectedIconColor = RojoInstitucional
                    ),
                    onClick = { navegarACerrarSesion() }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(titulo, color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = RojoInstitucional
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp
                ) {
                    bottomItems.forEach { item ->
                        val selected = selectedItem == item.ruta
                        NavigationBarItem(
                            selected = selected,
                            onClick = { if (!selected) item.accion() },
                            icon = {
                                Icon(
                                    imageVector = item.icono,
                                    contentDescription = item.label,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = RojoInstitucional,
                                selectedTextColor = RojoInstitucional,
                                indicatorColor = RojoInstitucional.copy(alpha = 0.12f),
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        ) { padding ->
            content(padding)
        }
    }
}