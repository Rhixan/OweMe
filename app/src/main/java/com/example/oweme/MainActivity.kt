package com.example.oweme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.oweme.features.auth.presentation.LoginScreen
import com.example.oweme.core.ui.theme.OwemeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enables full screen edge-to-edge drawing under status & nav bars
        enableEdgeToEdge()

        setContent {
            OwemeTheme {
                LoginScreen(
                    onNavigateToSignUp = {
                        // We will plug Jetpack Navigation here next
                    },
                    onLoginSuccess = {
                        // Navigate to home/dashboard
                    }
                )
            }
        }
    }
}