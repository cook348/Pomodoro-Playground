package com.example.pomodoroplayground.timer

import android.os.CountDownTimer

class PomodoroCountdownTimer(
    private val durationSeconds: Int,
    private val countdownIntervalSeconds: Int = 1,
    tickListener: (millisUntilFinished: Long) -> Unit,
    finishListener: () -> Unit
) {

    private val countdownTimer = object : CountDownTimer(
        durationSeconds * 1000L,
        countdownIntervalSeconds * 1000L
    ) {
        override fun onTick(p0: Long) {
            tickListener.invoke(p0)
        }

        override fun onFinish() {
            finishListener.invoke()
        }

    }

    fun start() {

    }

    fun pause() {

    }

    fun continueTimer() {

    }

    fun stop() {

    }



}