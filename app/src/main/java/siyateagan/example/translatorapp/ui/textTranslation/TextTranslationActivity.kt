package siyateagan.example.translatorapp.ui.textTranslation

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_text_translation.*
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.ui.selectLanguage.SelectLanguage
import siyateagan.example.translatorapp.ui.baseActivities.BaseNavigationActivity


class TextTranslationActivity : BaseNavigationActivity() {
    private lateinit var textTranslationViewModel: TextTranslationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textTranslationViewModel = ViewModelProvider(this).get(TextTranslationViewModel::class.java)

        setKeyboardDoneButton()

        input_language_button.setOnClickListener {
            val intent = Intent(this, SelectLanguage::class.java)
            this.startActivity(intent)
        }
    }

    override fun getLayoutResource() = R.layout.activity_text_translation
    override fun getClassName() = this::class.java.simpleName

    private fun setKeyboardDoneButton() {
        input_text.imeOptions = EditorInfo.IME_ACTION_DONE
        input_text.setRawInputType(InputType.TYPE_CLASS_TEXT)
    }
}