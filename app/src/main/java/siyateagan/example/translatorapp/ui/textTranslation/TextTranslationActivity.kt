package siyateagan.example.translatorapp.ui.textTranslation

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.databinding.ActivityTextTranslationBinding
import siyateagan.example.translatorapp.network.ResponseStatus
import siyateagan.example.translatorapp.ui.base.BaseNavigationActivity
import siyateagan.example.translatorapp.ui.base.interfaces.OnRetryClick
import siyateagan.example.translatorapp.ui.selectLanguage.SelectLanguageActivity
import siyateagan.example.translatorapp.util.afterTextChangedDelayed
import java.util.*
import javax.inject.Inject


class TextTranslationActivity : BaseNavigationActivity(), OnRetryClick {
    val TAG = TextTranslationActivity::class.java.simpleName

    private lateinit var binding: ActivityTextTranslationBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var textTranslationViewModel: TextTranslationViewModel

    private val disposables = CompositeDisposable()
    var timer = Timer()

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

        binding.swapLanguagesButton.setOnClickListener {
            textTranslationViewModel.swapLanguages()
            textTranslationViewModel.translateText()
        }

        textTranslationViewModel.setPreviousLanguages()

        binding.editTextToTranslate.afterTextChangedDelayed {
            textTranslationViewModel.translateText()
        }

        binding.buttonClear.setOnClickListener {
            textTranslationViewModel.translateObserver.translatedText.set("")
            binding.editTextToTranslate.text = null
        }

        val disposable = textTranslationViewModel.getResponseObservable().subscribe {
            when (it) {
                is ResponseStatus.Loading -> showLoading()
                is ResponseStatus.Success -> hideError()
                is ResponseStatus.Error -> showError(it.errorMessage)
            }
        }

        disposables.add(disposable)

        binding.errorInclude.listener = this
    }

    override fun onRetryClick() {
        hideError()
        textTranslationViewModel.translateText()
    }

    /** if the response comes long a message is displayed*/
    private fun showLoading() {
        timer.cancel()
        timer = Timer()
        timer.schedule(setLoadingMessage(), 400)
    }

    private fun setLoadingMessage() = object : TimerTask() {
        override fun run() {
            binding.translatedText.post {
                binding.translatedText.text = getString(R.string.loading_message)
            }
        }
    }

    private fun showError(error: String?) {
        timer.cancel()
        binding.translatedText.text = ""
        binding.errorInclude.errorLayout.visibility = View.VISIBLE
        binding.errorInclude.textErrorMessage.text = error
    }

    private fun hideError() {
        timer.cancel()
        binding.errorInclude.errorLayout.visibility = View.GONE
    }

    private fun setKeyboardDoneButton() {
        binding.editTextToTranslate.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.editTextToTranslate.setRawInputType(InputType.TYPE_CLASS_TEXT)
    }

    fun setLanguage(view: View) {
        val intent = Intent(this, SelectLanguageActivity::class.java)
        val requestCode: Int = if (view.id == binding.inputLanguageButton.id) 1 else 2
        this.startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        textTranslationViewModel.setNewLanguage(requestCode, data)
        textTranslationViewModel.translateText()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}