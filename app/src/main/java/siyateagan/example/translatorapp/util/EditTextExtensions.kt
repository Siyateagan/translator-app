package siyateagan.example.translatorapp.util

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton

fun EditText.setRestartTimerManager(timer: CountDownTimer, button: ImageButton) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            button.visibility = View.INVISIBLE
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            timer.cancel()
            timer.start()
        }
    })
}