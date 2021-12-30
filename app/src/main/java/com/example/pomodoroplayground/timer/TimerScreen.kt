package com.example.pomodoroplayground.timer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun TimerScreen(
    timerState: TimerState,
    onTimerStartClick: () -> Unit,
    onTimerPauseClick: () -> Unit,
    onTimerContinueClick: () -> Unit,
    onTimerStopClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {

        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
        ) {
            CircularProgressIndicator(
                progress = timerState.progress,
                strokeWidth = 24.dp,
                modifier = modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .padding(16.dp)
            )
            Text(
                text = timerState.getTimerText(),
                style = MaterialTheme.typography.h2,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        when (timerState.timerRunState) {
            TimerRunState.Ready -> {
                Button(onClick = onTimerStartClick) {
                    Text(text = "Start")
                }
            }
            TimerRunState.Running -> {
                Button(onClick = onTimerPauseClick) {
                    if (timerState.timerType == TimerType.Break) {
                        Text(text = "Skip")
                    } else {
                        Text(text = "Pause")
                    }
                }
            }
            TimerRunState.Paused -> {
                Row {
                    Button(onClick = onTimerContinueClick) {
                        Text(text = "Continue")
                    }

                    Button(onClick = onTimerStopClick) {
                        Text(text = "Stop")
                    }
                }
            }
        }

    }

}

@Preview
@Composable
fun PreviewTimerScreen() {
    TimerScreen(
        TimerState(),
        {}, {}, {}, {}
    )
}