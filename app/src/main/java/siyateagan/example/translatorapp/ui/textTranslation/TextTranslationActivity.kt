package siyateagan.example.translatorapp.ui.textTranslation

import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_favorites.nav_view
import kotlinx.android.synthetic.main.activity_text_translation.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.utils.setItemsListeners

class TextTranslationActivity : AppCompatActivity() {
    private lateinit var textTranslationViewModel: TextTranslationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translation)
        setSupportActionBar(toolbar)

        textTranslationViewModel = ViewModelProvider(this).get(TextTranslationViewModel::class.java)

        val navView: BottomNavigationView = nav_view
        navView.menu.getItem(0).isChecked = true
        setItemsListeners(navView, this, this::class.java.simpleName)

        setKeyboardDoneButton()
    }

    private fun setKeyboardDoneButton() {
        input_text.imeOptions = EditorInfo.IME_ACTION_DONE
        input_text.setRawInputType(InputType.TYPE_CLASS_TEXT)
    }
}