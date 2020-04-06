package siyateagan.example.translatorapp.util

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.setRestartTimerManager(timer: CountDownTimer) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            timer.cancel()
            timer.start()
        }
    })
}