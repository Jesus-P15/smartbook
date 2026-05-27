package co.edu.cecar.smartbooks.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import co.edu.cecar.smartbooks.screens.LoginScreen
import co.edu.cecar.smartbooks.screens.auth.RestablecerContrasenaScreen
import co.edu.cecar.smartbooks.ui.screen.ClientesScreen
import co.edu.cecar.smartbooks.ui.screen.DashboardScreen
import co.edu.cecar.smartbooks.ui.screen.InventariosScreen
import co.edu.cecar.smartbooks.ui.screen.LibrosScreen
import co.edu.cecar.smartbooks.ui.screen.LotesScreen
import co.edu.cecar.smartbooks.ui.screen.UsuariosScreen
import co.edu.cecar.smartbooks.ui.screen.VentasScreen
import co.edu.cecar.smartbooks.ui.screen.clientes.EditarClienteScreen
import co.edu.cecar.smartbooks.ui.screen.clientes.NuevoClienteScreen
import co.edu.cecar.smartbooks.ui.screen.libros.NuevoLibroScreen
import co.edu.cecar.smartbooks.ui.screen.libros.componentsLibros.EditarLibroScreen
import co.edu.cecar.smartbooks.viewmodel.ClientesViewModel


@Composable
fun AppNavigation() {

    val backStack = rememberNavBackStack(LoginRoute)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },

        entryProvider = entryProvider {

            entry<LoginRoute> {
               LoginScreen(
                    navegarADashboard = {
                        backStack.add(DashboardRoute)
                    },
                    navegarAPantallaRestablecerContrasena = {
                        backStack.add(RestablecerContrasenaRoute)
                    }
                )
            }

            // DASHBOARD
            entry<DashboardRoute> {

                DashboardScreen(

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

                    navegarACerrarSesion = {
                        backStack.clear()
                        backStack.add(LoginRoute)
                    }
                )
            }

            entry<ClientesRoute> {

                val clientesViewModel: ClientesViewModel = viewModel()

                ClientesScreen(


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

                navegarACerrarSesion = {
                    backStack.clear()
                    backStack.add(LoginRoute)
                },

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
                    navegarACerrarSesion = {
                        backStack.clear()
                        backStack.add(LoginRoute)
                    },

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

                    navegarACerrarSesion = {

                        backStack.clear()
                        backStack.add(LoginRoute)
                    }
                )
            }

            entry<LotesRoute> {
                LotesScreen(

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

                    navegarACerrarSesion = {

                        backStack.clear()
                        backStack.add(LoginRoute)
                    }
                )
            }

            entry<InventariosRoute> {
                InventariosScreen(

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

                    navegarACerrarSesion = {

                        backStack.clear()
                        backStack.add(LoginRoute)
                    }
                )
            }

            entry<UsuariosRoute> {
                UsuariosScreen(

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


                    navegarACerrarSesion = {

                        backStack.clear()
                        backStack.add(LoginRoute)
                    }
                )
            }
            entry<RestablecerContrasenaRoute> {
              RestablecerContrasenaScreen(

                    navegarALogin = {
                        backStack.removeLastOrNull()
                    }

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


        }
    )
}
