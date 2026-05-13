package co.edu.cecar.smartbooks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import co.edu.cecar.smartbooks.navigation.AppNavigation

import co.edu.cecar.smartbooks.screens.LoginScreen
import co.edu.cecar.smartbooks.ui.theme.SmartBooksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartBooksTheme {
                    AppNavigation()
                }
            }
        }
    }


