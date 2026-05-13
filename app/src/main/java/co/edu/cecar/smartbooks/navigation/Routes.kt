package co.edu.cecar.smartbooks.navigation


import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute: NavKey

@Serializable
data object DashboardRoute: NavKey

@Serializable
data object ClientesRoute: NavKey

@Serializable
data object LibrosRoute: NavKey

@Serializable
data object VentasRoute: NavKey

@Serializable
data object LotesRoute: NavKey

@Serializable
data object InventariosRoute: NavKey

@Serializable
data object UsuariosRoute: NavKey

@Serializable
data object PerfilRoute: NavKey
@Serializable
data object RestablecerContrasenaRoute: NavKey
