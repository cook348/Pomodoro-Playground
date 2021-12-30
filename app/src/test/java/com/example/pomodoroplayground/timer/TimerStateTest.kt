package com.example.pomodoroplayground.timer

import org.junit.Assert.assertEquals
import org.junit.Test

class TimerStateTest {

    @Test
    fun `25 minutes formatted properly`() {
        val state = TimerState()
        assertEquals("25:00", state.getTimerText())
        assertEquals(0f, state.progress)
    }

    @Test
    fun `24 min 59 seconds formatted correctly`() {
        val state = TimerState(millisUntilFinished = defaultPomodoroLength * 1000L - 1000)
        assertEquals("24:59", state.getTimerText())
        assertEquals("Not equal", 1 / 1500f, state.progress, 0.1f)
    }

    @Test
    fun `5 minutes formatted properly`() {
        val state = TimerState(durationSeconds = defaultBreakLength)
        assertEquals("5:00", state.getTimerText())
        assertEquals(0f, state.progress)
    }

    @Test
    fun `12 min 30 seconds out of 25 formatted correctly`() {
        val state = TimerState(millisUntilFinished = 750 * 1000L)
        assertEquals("12:30", state.getTimerText())
        assertEquals("Not equal", 0.5f, state.progress, 0.1f)
    }

    @Test
    fun `1 second remaining formatted correctly`() {
        val state = TimerState(millisUntilFinished = 1000L)
        assertEquals("0:01", state.getTimerText())
        assertEquals("Not equal", 1499 / 1500f, state.progress, 0.1f)
    }

    @Test
    fun `zero seconds formatted properly`() {
        val state =
            TimerState(durationSeconds = defaultBreakLength, millisUntilFinished = 0)
        assertEquals("0:00", state.getTimerText())
        assertEquals(1f, state.progress)
    }

    @Test
    fun `stopping pomodoro after time has elapsed returns new pomorodo of full length ready to run`() {
        val timerInit = TimerState.initializePomodoro()
        val timerRunning = timerInit.setMillisRemaining(20 * 1000L)
        val stopped = timerRunning.stopTimer()
        assertEquals(TimerState.initializePomodoro(), stopped)
    }

    @Test
    fun `stopping break after time has elapsed returns a new pomodoro of full lenth ready to run`() {
        val timerInit = TimerState.initializeBreak()
        val timerRunning = timerInit.setMillisRemaining(20 * 1000L)
        val stopped = timerRunning.stopTimer()
        assertEquals(TimerState.initializePomodoro(), stopped)

    }

    @Test
    fun `completing pomodoro returns break timer initial state`() {
        val timerInit = TimerState.initializePomodoro()
        val complete = timerInit.completeTimer()
        assertEquals(TimerState.initializeBreak(), complete)
    }

    @Test
    fun `completing a break returns pomodoro timer initial state`() {
        val timerInit = TimerState.initializeBreak()
        val complete = timerInit.completeTimer()
        assertEquals(TimerState.initializePomodoro(), complete)
    }

}