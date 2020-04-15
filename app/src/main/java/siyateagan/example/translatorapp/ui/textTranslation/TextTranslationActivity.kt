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
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.data.local.ResponseStatus
import siyateagan.example.translatorapp.databinding.ActivityTextTranslationBinding
import siyateagan.example.translatorapp.ui.base.BaseNavigationActivity
import siyateagan.example.translatorapp.ui.base.interfaces.OnRetryClick
import siyateagan.example.translatorapp.ui.selectLanguage.SelectLanguageActivity
import siyateagan.example.translatorapp.util.getOnFinishTimer
import siyateagan.example.translatorapp.util.setRestartTimerManager
import javax.inject.Inject


class TextTranslationActivity : BaseNavigationActivity(), OnRetryClick {
    private lateinit var binding: ActivityTextTranslationBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var textTranslationVM: TextTranslationVM

    private val responseTimer = getOnFinishTimer(400, ::setLoadingMessage)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_text_translation)
        textTranslationVM =
            ViewModelProvider(this, viewModelFactory).get(TextTranslationVM::class.java)
        binding.viewModel = textTranslationVM

        setSupportActionBar(binding.layoutToolbar.toolbar)
        setItemsIntents(binding.layoutNavigation.navView, this, this::class.java.simpleName)
        setKeyboardDoneButton()

        textTranslationVM.setPreviousLanguages()
        binding.errorLayout.listener = this

        binding.swapLanguagesButton.setOnClickListener {
            textTranslationVM.swapLanguages()
            textTranslationVM.translateText()
        }

        val requestTimer: CountDownTimer =
            getOnFinishTimer(500, textTranslationVM::translateText)
        binding.editTextToTranslate.setRestartTimerManager(requestTimer, binding.buttonFavorites)

        binding.buttonClear.setOnClickListener {
            textTranslationVM.translatedText.set("")
            binding.editTextToTranslate.text = null
        }

        binding.buttonFavorites.setOnClickListener {
            val favDisposable = textTranslationVM.addToFavorites()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> setFavoritesButtonColor(result) }
            addDisposable(favDisposable)
        }

        val responseDisposable = textTranslationVM.getResponseObservable().subscribe {
            when (it) {
                is ResponseStatus.Loading -> showLoading()
                is ResponseStatus.Success -> showSuccess()
                is ResponseStatus.Error -> showError(it.errorMessage)
            }
        }
        addDisposable(responseDisposable)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textTranslationVM.initTts()
            listOf(binding.buttonListenInput, binding.buttonListenOutput).forEach { imageButton ->
                imageButton.setOnClickListener { speakOut(imageButton.id) }
            }
        }

        val colorDisposable = textTranslationVM.isFavoritesColored.observable.subscribe {
            setFavoritesButtonColor(it)
            binding.buttonFavorites.visibility = View.VISIBLE
        }
        addDisposable(colorDisposable)
    }

    private fun setFavoritesButtonColor(result: Boolean) {
        if (result)
            binding.buttonFavorites.setColorFilter(
                ContextCompat.getColor(this, R.color.colorAccent)
            )
        else
            binding.buttonFavorites.setColorFilter(
                ContextCompat.getColor(this, R.color.primaryText)
            )
    }

    override fun onRetryClick() {
        showLoading()
        textTranslationVM.translateText()
    }

    /** if the response comes long a message is displayed*/
    private fun showLoading() {
        responseTimer.cancel()
        responseTimer.start()
        binding.errorLayout.errorLayout.visibility = View.GONE
    }

    private fun setLoadingMessage() {
        binding.translatedText.text = getString(R.string.loading_message)
    }

    private fun getImageButtons() = listOf(binding.buttonListenOutput, binding.buttonFavorites)

    private fun showError(error: String?) {
        responseTimer.cancel()
        binding.translatedText.text = ""
        binding.errorLayout.errorLayout.visibility = View.VISIBLE
        binding.errorLayout.textErrorMessage.text = error
        getImageButtons().forEach { it.visibility = View.INVISIBLE }
    }

    private fun showSuccess() {
        responseTimer.cancel()
        binding.errorLayout.errorLayout.visibility = View.GONE
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
        textTranslationVM.setNewLanguage(requestCode, data)
        textTranslationVM.translateText()

        if (requestCode == 1) textTranslationVM.setTtsLanguage(getString(R.string.current_button))
        else textTranslationVM.setTtsLanguage(getString(R.string.target_button))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun speakOut(buttonId: Int) {
        val text: CharSequence?
        val tts: TextToSpeech?
        if (buttonId == binding.buttonListenInput.id) {
            text = binding.editTextToTranslate.text
            tts = textTranslationVM.currentTextToSpeech
        } else {
            text = binding.translatedText.text
            tts = textTranslationVM.targetTextToSpeech
        }

        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}