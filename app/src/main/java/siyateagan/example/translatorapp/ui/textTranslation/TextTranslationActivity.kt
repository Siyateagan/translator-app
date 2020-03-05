package siyateagan.example.translatorapp.ui.textTranslation

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_text_translation.*
import kotlinx.android.synthetic.main.bottom_navigation_layout.*
import siyateagan.example.translatorapp.R
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import siyateagan.example.translatorapp.ui.base.BaseNavigationActivity
import siyateagan.example.translatorapp.ui.selectLanguage.SelectLanguage


class TextTranslationActivity : BaseNavigationActivity() {
    private lateinit var textTranslationViewModel: TextTranslationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translation)
        setSupportActionBar(toolbar)
        setItemsIntents(nav_view, this, this::class.java.simpleName)

        textTranslationViewModel = ViewModelProvider(this).get(TextTranslationViewModel::class.java)

        setKeyboardDoneButton()

        input_language_button.setOnClickListener {
            val intent = Intent(this, SelectLanguage::class.java)
            this.startActivity(intent)
        }
    }

    private fun setKeyboardDoneButton() {
        input_text.imeOptions = EditorInfo.IME_ACTION_DONE
        input_text.setRawInputType(InputType.TYPE_CLASS_TEXT)
    }
}