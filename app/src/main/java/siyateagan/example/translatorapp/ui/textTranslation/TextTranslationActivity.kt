package siyateagan.example.translatorapp.ui.textTranslation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.RequiresApi
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
import siyateagan.example.translatorapp.util.getOnFinishTimer
import siyateagan.example.translatorapp.util.setRestartTimerManager
import java.util.*
import javax.inject.Inject


class TextTranslationActivity : BaseNavigationActivity(), OnRetryClick {
    val TAG = TextTranslationActivity::class.java.simpleName

    private lateinit var binding: ActivityTextTranslationBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var textTranslationViewModel: TextTranslationViewModel

    private val disposables = CompositeDisposable()
    private val timer = getOnFinishTimer(400, ::setLoadingMessage)
    private var currentTextToSpeech: TextToSpeech? = null
    private var targetTextToSpeech: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_text_translation)

        setSupportActionBar(binding.layoutToolbar.toolbar)
        setItemsIntents(binding.layoutNavigation.navView, this, this::class.java.simpleName)
        setKeyboardDoneButton()

        textTranslationViewModel =
            ViewModelProvider(this, viewModelFactory).get(TextTranslationViewModel::class.java)

        binding.viewModel = textTranslationViewModel

        binding.swapLanguagesButton.setOnClickListener {
            textTranslationViewModel.swapLanguages()
            textTranslationViewModel.translateText()
            setTtsLanguage(textTranslationViewModel.currentButton)
            setTtsLanguage(textTranslationViewModel.targetButton)
        }

        textTranslationViewModel.setPreviousLanguages()

        val requestTimer: CountDownTimer =
            getOnFinishTimer(500, textTranslationViewModel::translateText)
        binding.editTextToTranslate.setRestartTimerManager(requestTimer)

        binding.buttonClear.setOnClickListener {
            textTranslationViewModel.translateObserver.translatedText.set("")
            binding.editTextToTranslate.text = null
        }

        val disposable = textTranslationViewModel.getResponseObservable().subscribe {
            when (it) {
                is ResponseStatus.Loading -> showLoading()
                is ResponseStatus.Success -> showSuccess()
                is ResponseStatus.Error -> showError(it.errorMessage)
            }
        }

        disposables.add(disposable)

        binding.errorInclude.listener = this

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            currentTextToSpeech =
                TextToSpeech(
                    this,
                    TextToSpeech.OnInitListener { setTtsLanguage(textTranslationViewModel.currentButton) })
            targetTextToSpeech =
                TextToSpeech(
                    this,
                    TextToSpeech.OnInitListener { setTtsLanguage(textTranslationViewModel.targetButton) })

            listOf(binding.buttonListenInput, binding.buttonListenOutput).forEach { imageButton ->
                imageButton.setOnClickListener { speakOut(imageButton.id) }
            }
        }
    }

    override fun onRetryClick() {
        showLoading()
        textTranslationViewModel.translateText()
    }

    /** if the response comes long a message is displayed*/
    private fun showLoading() {
        timer.cancel()
        timer.start()
        binding.errorInclude.errorLayout.visibility = View.GONE
    }

    private fun setLoadingMessage() {
        binding.translatedText.text = getString(R.string.loading_message)
    }

    private fun getImageButtons() = listOf(binding.buttonListenOutput, binding.buttonFavorites)

    private fun showError(error: String?) {
        timer.cancel()
        binding.translatedText.text = ""
        binding.errorInclude.errorLayout.visibility = View.VISIBLE
        binding.errorInclude.textErrorMessage.text = error
        getImageButtons().forEach { it.visibility = View.INVISIBLE }
    }

    private fun showSuccess() {
        timer.cancel()
        binding.errorInclude.errorLayout.visibility = View.GONE
        getImageButtons().forEach { it.visibility = View.VISIBLE }
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

        if (requestCode == 1) setTtsLanguage(textTranslationViewModel.currentButton)
        else setTtsLanguage(textTranslationViewModel.targetButton)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun speakOut(buttonId: Int) {
        val text: CharSequence
        if (buttonId == binding.buttonListenInput.id) {
            text = binding.editTextToTranslate.text
            currentTextToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        } else {
            text = binding.translatedText.text
            targetTextToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    private fun setTtsLanguage(button: LanguageButton) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
        if (button.languageCode.isNullOrEmpty()) return

        val languageCode: String = button.languageCode!!
        if (button == textTranslationViewModel.currentButton)
            currentTextToSpeech?.language = Locale.forLanguageTag(languageCode)
        else targetTextToSpeech?.language = Locale.forLanguageTag(languageCode)
    }
}
