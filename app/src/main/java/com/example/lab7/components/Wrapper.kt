package com.example.lab7.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lab7.R

class Wrapper {
    companion object {
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun Wrapper(navController: NavController, snackbarHost: @Composable () -> Unit = {}, floatingActionButton: @Composable () -> Unit = {}, content: @Composable () -> Unit) {
            Scaffold (
                snackbarHost = snackbarHost,
                topBar = {
                    TopAppBar(
                        title = {
                            Text("Lab 7")
                        }
                    )
                },
                bottomBar = {
                    val primaryCol = MaterialTheme.colorScheme.primary
                    val accentCol = MaterialTheme.colorScheme.secondary
                    val route = navController.currentBackStackEntry?.destination?.route

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(MaterialTheme.colorScheme.primary),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button (
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            shape = RectangleShape,
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            onClick = {
                                if (route != "homePage")
                                    navController.navigate("homePage")
                            }
                        ) {
                            Column (
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    painter = painterResource(id = R.drawable.account_school),
                                    contentDescription = "Home Page icon",
                                    modifier = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .background(if (route == "homePage") accentCol else primaryCol)
                                )
                            }
                        }
                        Button (
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            shape = RectangleShape,
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            onClick = {
                                if (route != "recordingPage")
                                    navController.navigate("recordingPage")
                            }
                        ) {
                            Column (
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    painter = painterResource(id = R.drawable.microphone),
                                    contentDescription = "Information Icon",
                                    modifier = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .background(if (route == "recordingPage") accentCol else primaryCol)
                                )
                            }
                        }
                    }
                },
                floatingActionButton = floatingActionButton
            ) { paddingValues ->
                Box(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    content()
                }
            }
        }
    }
}