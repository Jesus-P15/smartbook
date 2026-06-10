package co.edu.cecar.smartbooks.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import co.edu.cecar.smartbooks.data.network.SessionManager
import co.edu.cecar.smartbooks.screens.LoginScreen
import co.edu.cecar.smartbooks.screens.auth.RestablecerContrasenaScreen
import co.edu.cecar.smartbooks.ui.screen.ClientesScreen
import co.edu.cecar.smartbooks.ui.screen.DashboardScreen
import co.edu.cecar.smartbooks.ui.screen.InventariosScreen
import co.edu.cecar.smartbooks.ui.screen.LibrosScreen
import co.edu.cecar.smartbooks.ui.screen.LotesScreen
import co.edu.cecar.smartbooks.ui.screen.UsuariosScreen
import co.edu.cecar.smartbooks.ui.screen.VentasScreen
import co.edu.cecar.smartbooks.ui.screen.auth.SolicitudRestablecimientoScreen
import co.edu.cecar.smartbooks.ui.screen.clientes.EditarClienteScreen
import co.edu.cecar.smartbooks.ui.screen.clientes.NuevoClienteScreen
import co.edu.cecar.smartbooks.ui.screen.libros.NuevoLibroScreen
import co.edu.cecar.smartbooks.ui.screen.libros.componentsLibros.EditarLibroScreen
import co.edu.cecar.smartbooks.ui.screen.perfil.PerfilScreen
import co.edu.cecar.smartbooks.viewmodel.ClientesViewModel
import co.edu.cecar.smartbooks.viewmodel.LoginViewModel


@Composable
fun AppNavigation() {

    val backStack = rememberNavBackStack(LoginRoute)
    val context = androidx.compose.ui.platform.LocalContext.current

    val loginViewModel: LoginViewModel = viewModel()

    fun cerrarSesion() {
        loginViewModel.resetear()
        SessionManager.cerrarSesion(context)
        backStack.clear()
        backStack.add(LoginRoute)
    }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },

        entryProvider = entryProvider {

            entry<LoginRoute> {
               LoginScreen(
                   viewModel = loginViewModel,
                   navegarADashboard = {
                        backStack.add(DashboardRoute)
                    },
                    navegarAPantallaRestablecerContrasena = {
                        backStack.add(SolicitudRestablecimientoRoute)
                    }
                )
            }

            // DASHBOARD
            entry<DashboardRoute> {

                DashboardScreen(
                    navegarAPerfil = { backStack.add(PerfilRoute) },

                    navegarAClientes = {
                        backStack.add(ClientesRoute)
                    },

                    navegarALibros = {
                        backStack.add(LibrosRoute)
                    },

                    navegarAVentas = {
                        backStack.add(VentasRoute)
                    },

                    navegarALotes = {
                        backStack.add(LotesRoute)
                    },

                    navegarAInventarios = {
                        backStack.add(InventariosRoute)
                    },

                    navegarAUsuarios = {
                        backStack.add(UsuariosRoute)
                    },
                    navegarACerrarSesion = {cerrarSesion()}
                )
            }

            entry<ClientesRoute> {



                ClientesScreen(

                    navegarAPerfil = { backStack.add(PerfilRoute) },

                navegarADashboard = {

                    backStack.clear()
                    backStack.add(DashboardRoute)
                },

                navegarALibros = {
                    backStack.add(LibrosRoute)
                },

                navegarAVentas = {
                    backStack.add(VentasRoute)
                },

                navegarALotes = {
                    backStack.add(LotesRoute)
                },

                navegarAInventarios = {
                    backStack.add(InventariosRoute)
                },

                navegarAUsuarios = {
                    backStack.add(UsuariosRoute)
                },

                navegarACerrarSesion = {cerrarSesion()},

                navegarANuevoCliente = {
                    backStack.add(NuevoClienteRoute)
                },

                navegarAEditarCliente = {identificacion ->
                    backStack.add(EditarClienteRoute(identificacion))
                }
            )
            }

            entry<LibrosRoute> {


                var libroCreado by remember { mutableStateOf(false) }
                LibrosScreen(

                    navegarAPerfil = { backStack.add(PerfilRoute) },

                    navegarADashboard = {

                        backStack.clear()
                        backStack.add(DashboardRoute)
                    },

                    navegarAClientes = {
                        backStack.add(ClientesRoute)
                    },
                    navegarAVentas = {
                        backStack.add(VentasRoute)
                    },

                    navegarALotes = {
                        backStack.add(LotesRoute)
                    },

                    navegarAInventarios = {
                        backStack.add(InventariosRoute)
                    },

                    navegarAUsuarios = {
                        backStack.add(UsuariosRoute)
                    },
                    navegarACerrarSesion = {cerrarSesion() },

                    libroCreado = libroCreado,

                    navegarANuevoLibro = {
                        backStack.add(NuevoLibroRoute)
                    },
                    navegarAEditarLibro = {libroId ->
                        backStack.add(EditarLibroRoute(libroId))
                    }

                )
            }

            entry<VentasRoute> {
                VentasScreen(

                    navegarAPerfil = { backStack.add(PerfilRoute) },

                    navegarADashboard = {

                        backStack.clear()
                        backStack.add(DashboardRoute)
                    },

                    navegarAClientes = {
                        backStack.add(ClientesRoute)
                    },

                    navegarALibros = {
                        backStack.add(LibrosRoute)
                    },

                    navegarALotes = {
                        backStack.add(LotesRoute)
                    },

                    navegarAInventarios = {
                        backStack.add(InventariosRoute)
                    },

                    navegarAUsuarios = {
                        backStack.add(UsuariosRoute)
                    },

                    navegarACerrarSesion = {cerrarSesion()}
                )
            }

            entry<LotesRoute> {
                LotesScreen(

                    navegarAPerfil = { backStack.add(PerfilRoute) },

                    navegarADashboard = {

                        backStack.clear()
                        backStack.add(DashboardRoute)
                    },

                    navegarAClientes = {
                        backStack.add(ClientesRoute)
                    },

                    navegarALibros = {
                        backStack.add(LibrosRoute)
                    },

                    navegarAVentas = {
                        backStack.add(VentasRoute)
                    },

                    navegarALotes = {},

                    navegarAInventarios = {
                        backStack.add(InventariosRoute)
                    },

                    navegarAUsuarios = {
                        backStack.add(UsuariosRoute)
                    },

                    navegarACerrarSesion = {cerrarSesion()}
                )
            }

            entry<InventariosRoute> {
                InventariosScreen(

                    navegarAPerfil = { backStack.add(PerfilRoute) },

                    navegarADashboard = {

                        backStack.clear()
                        backStack.add(DashboardRoute)
                    },

                    navegarAClientes = {
                        backStack.add(ClientesRoute)
                    },

                    navegarALibros = {
                        backStack.add(LibrosRoute)
                    },

                    navegarAVentas = {
                        backStack.add(VentasRoute)
                    },

                    navegarALotes = {
                        backStack.add(LotesRoute)
                    },


                    navegarAUsuarios = {
                        backStack.add(UsuariosRoute)
                    },

                    navegarACerrarSesion = {cerrarSesion() }
                )
            }

            entry<UsuariosRoute> {
                val rol = remember { SessionManager.obtenerRol() }
                if (!rol.equals("Admin", ignoreCase = true)) {
                    LaunchedEffect(Unit) {
                        backStack.clear()
                        backStack.add(DashboardRoute)
                    }
                    return@entry
                }
                UsuariosScreen(

                    navegarAPerfil = { backStack.add(PerfilRoute) },

                    navegarADashboard = {

                        backStack.clear()
                        backStack.add(DashboardRoute)
                    },

                    navegarAClientes = {
                        backStack.add(ClientesRoute)
                    },

                    navegarALibros = {
                        backStack.add(LibrosRoute)
                    },

                    navegarAVentas = {
                        backStack.add(VentasRoute)
                    },

                    navegarALotes = {
                        backStack.add(LotesRoute)
                    },

                    navegarAInventarios = {
                        backStack.add(InventariosRoute)
                    },


                    navegarACerrarSesion = {cerrarSesion()}
                )
            }




            entry<NuevoLibroRoute> {

                NuevoLibroScreen(

                    onLibroCreado = {

                        backStack.removeLastOrNull()
                    },
                    navegarAtras = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<EditarLibroRoute> {
                println("LIBRO ID RECIBIDO: ${it.libroId}")
                EditarLibroScreen(
                    libroId = it.libroId,
                    navegarAtras = { backStack.removeLastOrNull() }
                )
            }


            entry<NuevoClienteRoute> {
                NuevoClienteScreen(

                    onClienteCreado = {
                        backStack.removeLastOrNull()
                    },
                    navegarAtras = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<EditarClienteRoute> {
                EditarClienteScreen(
                    identificacion = it.identificacion,
                    navegarAtras = { backStack.removeLastOrNull() }
                )
            }

            entry<SolicitudRestablecimientoRoute> {
                SolicitudRestablecimientoScreen(
                    navegarARestablecer = {
                        backStack.add(RestablecerConCodigoRoute)
                    },
                    navegarAtras = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<RestablecerConCodigoRoute> {
                RestablecerContrasenaScreen(
                    navegarALogin = {
                        backStack.clear()
                        backStack.add(LoginRoute)
                    }
                )
            }

            entry<PerfilRoute> {
                PerfilScreen(
                    navegarADashboard = {
                        backStack.clear()
                        backStack.add(DashboardRoute)
                    },
                    navegarAClientes = {
                        backStack.add(ClientesRoute)
                    },
                    navegarALibros = {
                        backStack.add(LibrosRoute)
                    },
                    navegarAVentas = {
                        backStack.add(VentasRoute)
                    },
                    navegarALotes = {
                        backStack.add(LotesRoute)
                    },
                    navegarAInventarios = {
                        backStack.add(InventariosRoute)
                    },
                    navegarAUsuarios = {
                        backStack.add(UsuariosRoute)
                    },
                    navegarACerrarSesion = { cerrarSesion() }
                )
            }
        }
    )
}
