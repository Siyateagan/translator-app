package siyateagan.example.translatorapp.util

import android.os.CountDownTimer
import android.view.View
import android.widget.ImageButton

fun getOnFinishTimer(delay: Long, onTimerFinish: () -> Unit): CountDownTimer {
    return object : CountDownTimer(delay, delay) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            onTimerFinish()
        }
    }
}

fun getOnFinishTimer(delay: Long, button: ImageButton, onTimerFinish: () -> Unit): CountDownTimer {
    return object : CountDownTimer(delay, delay) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            button.visibility = View.VISIBLE
            onTimerFinish()
        }
    }
}