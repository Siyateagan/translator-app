package siyateagan.example.translatorapp.util

import android.os.CountDownTimer

fun getOnFinishTimer(delay: Long, onTimerFinish: () -> Unit): CountDownTimer {
    return object : CountDownTimer(delay, delay) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            onTimerFinish()
        }
    }
}