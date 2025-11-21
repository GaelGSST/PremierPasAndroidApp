package com.example.premierpas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.navigation.compose.rememberNavController
import com.example.premierpas.ui.navigation.ApplicationNavHost
import com.example.premierpas.ui.theme.PremierPasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PremierPasTheme {
                Box {
                    val navController = rememberNavController()
                    ApplicationNavHost(
                        navController = navController,
                    )
                }
            }
        }
    }
}
