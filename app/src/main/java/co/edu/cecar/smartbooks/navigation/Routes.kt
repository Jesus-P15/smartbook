package co.edu.cecar.smartbooks.navigation


import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable



@Serializable
data object NuevoClienteRoute: NavKey


@Serializable
data object NuevoLibroRoute: NavKey

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


// Data class para editar

@Serializable
data class EditarClienteRoute(val identificacion: String): NavKey

@Serializable
data class  EditarLibroRoute(val libroId: Int): NavKey