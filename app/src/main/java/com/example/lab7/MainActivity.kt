package com.example.lab7

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab7.model.Student
import com.example.lab7.model.StudentViewModel
import com.example.lab7.pages.HomePage.Companion.HomePage
import com.example.lab7.pages.RandomStudentPage.Companion.RandomStudentPage
import com.example.lab7.pages.RecordingPage.Companion.RecordingPage
import com.example.lab7.ui.theme.Lab7Theme
import android.Manifest
import android.view.View
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = StudentViewModel(
            listOf(
                Student("h2210147", "Gun Rui", "M25404"),
                Student("h2210066", "Jayden", "M25404"),
                Student("h2210120", "Zhehan", "M25404"),
                Student("h2210102","Logan", "M25404"),
                Student("h2210169", "Liqian", "M25405")
            )
        )

        super.onCreate(savedInstanceState)
        enableImmersiveMode()
        enableEdgeToEdge()
        setContent {
            Lab7Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                MainApp(viewModel)
                }
            }
        }
    }

    private fun enableImmersiveMode() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
    }

    @Composable
    private fun MainApp(viewModel: StudentViewModel) {
        val navController = rememberNavController()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            ActivityCompat.requestPermissions(
                (context as ComponentActivity),
                arrayOf(Manifest.permission.RECORD_AUDIO),
                0
            )
        }

        NavHost(
            navController = navController,
            startDestination = "recordingPage"
        ) {
            composable("homePage") {HomePage(navController, viewModel)}
            composable(
                "randomStudentPage/{index}",
                arguments = listOf(
                    navArgument("index") {type = NavType.StringType}
                )
            ) { backStackEntry ->
                val index = backStackEntry.arguments?.getString("index")
                if (index != null) {
                    RandomStudentPage(navController, index.toInt(), viewModel)
                }
            }
            composable("recordingPage") {RecordingPage(navController)}
        }
    }
}