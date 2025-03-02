package com.example.lab7.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lab7.components.Wrapper.Companion.Wrapper
import com.example.lab7.model.Student
import com.example.lab7.model.StudentViewModel
import com.example.lab7.pages.HomePage.Companion.StudentCard

class RandomStudentPage {
    companion object {
        @Composable
        fun RandomStudentPage(navController: NavController, index: Int, viewModel: StudentViewModel) {
            Wrapper(navController) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Random Student",
                        style = MaterialTheme.typography.titleLarge
                    )
                    StudentCard(viewModel.students[index])
                    Button(
                        onClick = {
                            navController.navigate("homePage")
                        }
                    ) {
                        Text("Back")
                    }
                }
            }
        }
    }
}