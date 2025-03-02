package com.example.lab7.pages

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lab7.components.Wrapper.Companion.Wrapper
import com.example.lab7.model.Student
import com.example.lab7.model.StudentViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab7.components.ShakeDetector
import kotlin.random.Random

class HomePage {
    companion object {
        @Composable
        fun HomePage(navController: NavController, viewModel: StudentViewModel) {
            val context = LocalContext.current

            DisposableEffect(Unit) {
                val shakeDetector = ShakeDetector(context) {
                    val currentTime = System.currentTimeMillis().toInt()
                    val random = Random(currentTime)
                    val randomID = random.nextInt(0, viewModel.students.size)

                    navController.navigate("randomStudentPage/$randomID")
                }

                onDispose {
                    shakeDetector.unregister()
                }
            }

            Wrapper(navController) {
                LazyColumn {
                    items(viewModel.students) { student ->
                        StudentCard(student)
                    }
                }
            }
        }

        @Composable
        fun StudentCard(student: Student) {
            ElevatedCard (
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = student.studentID,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = student.name
                    )
                    Text(
                        text = student.mentorGroup
                    )
                }
            }
        }
    }
}