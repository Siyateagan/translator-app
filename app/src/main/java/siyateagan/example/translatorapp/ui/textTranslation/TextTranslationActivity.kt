package siyateagan.example.translatorapp.ui.textTranslation

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_text_translation.*
import kotlinx.android.synthetic.main.bottom_navigation_layout.*
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.databinding.ActivityTextTranslationBinding
import siyateagan.example.translatorapp.ui.base.BaseNavigationActivity
import siyateagan.example.translatorapp.ui.selectLanguage.SelectLanguageActivity
import javax.inject.Inject


class TextTranslationActivity : BaseNavigationActivity() {

    private lateinit var binding: ActivityTextTranslationBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var textTranslationViewModel: TextTranslationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_text_translation)

        setSupportActionBar(toolbar)
        setItemsIntents(nav_view, this, this::class.java.simpleName)
        setKeyboardDoneButton()

        textTranslationViewModel =
            ViewModelProvider(this, viewModelFactory).get(TextTranslationViewModel::class.java)

        binding.viewModel = textTranslationViewModel

        binding.inputLanguageButton.setOnClickListener {
            val intent = Intent(this, SelectLanguageActivity::class.java)
            this.startActivityForResult(intent, 1)
        }
    }

    private fun setKeyboardDoneButton() {
        input_text.imeOptions = EditorInfo.IME_ACTION_DONE
        input_text.setRawInputType(InputType.TYPE_CLASS_TEXT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        textTranslationViewModel.currentLanguage.set(data?.getStringExtra("language"))
        textTranslationViewModel.currentLanguage.notifyChange()
    }
}