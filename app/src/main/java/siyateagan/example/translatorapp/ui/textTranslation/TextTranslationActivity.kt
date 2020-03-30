package siyateagan.example.translatorapp.ui.textTranslation

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.databinding.ActivityTextTranslationBinding
import siyateagan.example.translatorapp.ui.base.BaseNavigationActivity
import siyateagan.example.translatorapp.ui.selectLanguage.SelectLanguageActivity
import siyateagan.example.translatorapp.util.afterTextChangedDelayed
import javax.inject.Inject


class TextTranslationActivity : BaseNavigationActivity() {
    val TAG = TextTranslationActivity::class.java.simpleName

    private lateinit var binding: ActivityTextTranslationBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var textTranslationViewModel: TextTranslationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_text_translation)

        setSupportActionBar(binding.toolbar)
        setItemsIntents(binding.navView, this, this::class.java.simpleName)
        setKeyboardDoneButton()

        textTranslationViewModel =
            ViewModelProvider(this, viewModelFactory).get(TextTranslationViewModel::class.java)

        binding.viewModel = textTranslationViewModel

        binding.inputLanguageButton.setOnClickListener {
            val intent = Intent(this, SelectLanguageActivity::class.java)
            this.startActivityForResult(intent, 1)
        }

        binding.translationLanguageButton.setOnClickListener {
            val intent = Intent(this, SelectLanguageActivity::class.java)
            this.startActivityForResult(intent, 2)
        }

        binding.swapLanguagesButton.setOnClickListener {
            textTranslationViewModel.swapLanguages()
        }

        textTranslationViewModel.setPreviousLanguages()

        binding.editTextToTranslate.afterTextChangedDelayed {
            if (it.isNotBlank()) textTranslationViewModel.translateText(it)
            else textTranslationViewModel.clearTranslatedText()
        }

        binding.buttonClear.setOnClickListener {
            textTranslationViewModel.clearTranslatedText()
            binding.editTextToTranslate.text = null
        }
    }

    private fun setKeyboardDoneButton() {
        binding.editTextToTranslate.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.editTextToTranslate.setRawInputType(InputType.TYPE_CLASS_TEXT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        textTranslationViewModel.setNewLanguage(requestCode, data)
    }
}