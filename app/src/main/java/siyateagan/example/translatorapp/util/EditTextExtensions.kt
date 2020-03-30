package siyateagan.example.translatorapp.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.*

fun EditText.afterTextChangedDelayed(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        private var timer = Timer()
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            timer.cancel();
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    afterTextChanged.invoke(editable.toString())
                }
            }, 500)
        }
    })
}