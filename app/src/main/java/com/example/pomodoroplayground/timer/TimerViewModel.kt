package com.example.pomodoroplayground.timer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import android.os.CountDownTimer




class TimerViewModel : ViewModel() {

    var timerState = mutableStateOf(
        TimerState(
            timerRunState = TimerRunState.Ready,
            timerType = TimerType.Pomodoro
        )
    )
        private set

    private val countdownTimer = object : CountDownTimer(30000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            timerState.value = timerState.value.setMillisRemaining(millisUntilFinished)
        }

        override fun onFinish() {
            timerState.value = timerState.value.completeTimer()
        }
    }

    fun startTimer() {
        countdownTimer.start()
        timerState.value = timerState.value.startTimer()
    }

    fun pauseTimer() {
        // TODO apparently the countdown timer doesn't have a pause function
        timerState.value = timerState.value.pauseTimer()
    }

    fun continueTimer() {
        // TODO continue countdown timer
        timerState.value = timerState.value.continueTimer()
    }

    fun stopTimer() {
        // TODO stop the countdown timer
        timerState.value = timerState.value.stopTimer()
    }


}