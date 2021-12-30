package com.example.pomodoroplayground.timer

const val defaultPomodoroLength = 25 * 60 // 25 minutes in seconds
const val defaultBreakLength = 5 * 60 // 5 minutes in seconds

/**
 * The State of the timer
 *  - Defaults to a Pomodoro timer, ready to run, 25 minute duration, 0 elapsed
 */
data class TimerState(
    val timerType: TimerType = TimerType.Pomodoro,
    val timerRunState: TimerRunState = TimerRunState.Ready,
    val durationSeconds: Int = defaultPomodoroLength,
    val millisUntilFinished: Long = durationSeconds * 1000L
) {

    val progress: Float =
        (durationSeconds - millisUntilFinished / 1000L) / durationSeconds.toFloat()

    /**
     * The text to display for the timer = duration - elapsed
     */
    fun getTimerText(): String {
        val remainingSeconds = millisUntilFinished / 1000
        val minutes = remainingSeconds / 60
        val seconds = remainingSeconds % 60
        return "%d:%02d".format(minutes, seconds)
    }

    fun startTimer(): TimerState = this.copy(timerRunState = TimerRunState.Running)

    /**
     * Stop the currently timer.
     * If it is a pomodoro, set the elapsed time to 0.
     * If it is a break, skip the break and start a pomodoro.
     */
    fun stopTimer(): TimerState {
        return when (timerType) {
            TimerType.Pomodoro -> initializePomodoro()
            TimerType.Break -> initializePomodoro()
        }
    }

    fun pauseTimer(): TimerState = this.copy(timerRunState = TimerRunState.Paused)

    fun continueTimer(): TimerState = this.copy(timerRunState = TimerRunState.Running)

    fun completeTimer(): TimerState {
        return when (timerType) {
            TimerType.Pomodoro -> initializeBreak()
            TimerType.Break -> initializePomodoro()
        }
    }

    fun setMillisRemaining(millisUntilFinished: Long): TimerState =
        this.copy(millisUntilFinished = millisUntilFinished)

    companion object {

        fun initializePomodoro(): TimerState = TimerState()

        fun initializeBreak(): TimerState =
            TimerState(timerType = TimerType.Break, durationSeconds = defaultBreakLength)
    }

}

enum class TimerType {
    Pomodoro,
    Break
}

enum class TimerRunState {
    Ready,
    Running,
    Paused
}