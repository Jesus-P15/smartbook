package co.edu.cecar.smartbooks.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import co.edu.cecar.smartbooks.screens.ClientesScreen
import co.edu.cecar.smartbooks.screens.DashboardScreen
import co.edu.cecar.smartbooks.screens.InventariosScreen
import co.edu.cecar.smartbooks.screens.LibrosScreen
import co.edu.cecar.smartbooks.screens.LoginScreen
import co.edu.cecar.smartbooks.screens.LotesScreen
import co.edu.cecar.smartbooks.screens.UsuariosScreen
import co.edu.cecar.smartbooks.screens.VentasScreen
import co.edu.cecar.smartbooks.screens.auth.RestablecerContrasenaScreen


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
                    navegarAPantallaRestablecerContrasena={
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
                    }

                )
            }


            entry<ClientesRoute> {
                ClientesScreen()
            }

            entry<LibrosRoute> {
                LibrosScreen()
            }

            entry<VentasRoute> {
                VentasScreen()
            }

            entry<LotesRoute> {
                LotesScreen()
            }

            entry<InventariosRoute> {
                InventariosScreen()
            }

            entry<UsuariosRoute> {
                UsuariosScreen()
            }
            entry<RestablecerContrasenaRoute> {

                RestablecerContrasenaScreen(

                    navegarALogin = {
                        backStack.removeLastOrNull()
                    }

                )

            }
        }
    )
}