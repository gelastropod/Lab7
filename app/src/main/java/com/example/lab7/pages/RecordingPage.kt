package com.example.lab7.pages

import android.content.Intent
import android.net.Uri
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lab7.R
import com.example.lab7.components.Wrapper.Companion.Wrapper
import com.example.lab7.model.SpeechToTextViewModel
import kotlinx.coroutines.launch

class RecordingPage {
    companion object {
        @Composable
        fun RecordingPage(navController: NavController) {
            val viewModel: SpeechToTextViewModel = viewModel()
            val context = LocalContext.current

            val actualText = remember{ mutableStateOf("")}
            val actualTime = remember{mutableStateOf(0L)}
            val recognizedText by viewModel.recognizedText.collectAsState()
            val isListening by viewModel.isListening.collectAsState()
            val isPaused = remember {mutableStateOf(false)}

            val chronometer = remember { mutableStateOf<Chronometer?>(null) }

            val snackbarHostState = remember { SnackbarHostState() }
            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(isListening) {
                if (isListening) {
                    chronometer.value?.base = SystemClock.elapsedRealtime()
                    chronometer.value?.start()
                } else {
                    chronometer.value?.stop()
                    isPaused.value = false;
                }
            }

            Wrapper(navController,
                snackbarHost = {
                    SnackbarHost(snackbarHostState)
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "An email has been sent to your module teacher!",
                                    actionLabel = "Ok",
                                    duration = SnackbarDuration.Short
                                )
                            }

                            val timeTaken = (SystemClock.elapsedRealtime() - chronometer.value?.base!!)/1000.0f
                            val recipient = ""
                            val subject = ""
                            val body = "Speech: ${actualText.value}$recognizedText\nTime taken: $timeTaken seconds"

                            val intent = Intent(
                                Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto",recipient, null)).apply {
                                putExtra(Intent.EXTRA_SUBJECT, subject)
                                putExtra(Intent.EXTRA_TEXT, body)
                            }

                            try {
                                context.startActivity(Intent.createChooser(intent, "Choose an email client"))
                            } catch (e: Exception) {
                                Log.e("EmailError", "No email client found", e)
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.email),
                            contentDescription = "Email"
                        )
                    }
                }) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Recording",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = actualText.value + recognizedText,
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row (
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Button(
                            onClick = {
                                if (!isListening) {
                                    actualText.value = ""
                                    viewModel.clearRecognizedText()
                                    viewModel.startListening()
                                } else {
                                    viewModel.stopListening()
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (isListening) "Stop Recording" else "Start Recording")
                        }

                        Button(
                            onClick = {
                                if (!isPaused.value) {
                                    isPaused.value = true
                                    actualText.value += "$recognizedText "
                                    viewModel.clearRecognizedText()
                                    chronometer.value?.stop()
                                    actualTime.value = SystemClock.elapsedRealtime() - chronometer.value?.base!!
                                } else {
                                    isPaused.value = false
                                    chronometer.value?.base = SystemClock.elapsedRealtime() - actualTime.value
                                    chronometer.value?.start()
                                    viewModel.startListening()
                                }
                            },
                            enabled = isListening,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (isPaused.value) "Resume Recording" else "Pause Recording")
                        }
                    }


                    Spacer(modifier = Modifier.height(16.dp))

                    AndroidView(
                        factory = { context ->
                            Chronometer(context).apply {
                                setTextColor(android.graphics.Color.WHITE)
                            }.also {
                                chronometer.value = it
                            }
                        }
                    )
                }
            }
        }
    }
}