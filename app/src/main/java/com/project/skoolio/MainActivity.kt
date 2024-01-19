package com.project.skoolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.skoolio.navigation.AppNavigation
import com.project.skoolio.ui.theme.SkoolioTheme
import com.project.skoolio.viewModels.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkoolioTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SkoolioApp()
                }
            }
        }
    }
}

@Composable
fun SkoolioApp(){
    val viewModelProvider:ViewModelProvider = viewModel()
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
        AppNavigation(viewModelProvider)
//    }
}