package co.edu.cecar.smartbooks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import co.edu.cecar.smartbooks.data.network.SessionManager
import co.edu.cecar.smartbooks.navigation.AppNavigation

import co.edu.cecar.smartbooks.screens.LoginScreen
import co.edu.cecar.smartbooks.ui.screen.ClientesScreen
import co.edu.cecar.smartbooks.ui.theme.SmartBooksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SessionManager.init(this)

        enableEdgeToEdge()
        setContent {
            SmartBooksTheme {
                AppNavigation()
                }
            }
        }
    }


