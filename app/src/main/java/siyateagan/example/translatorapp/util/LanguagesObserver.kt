package siyateagan.example.translatorapp.util

import android.content.Context
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.network.AvailableLanguages
import siyateagan.example.translatorapp.ui.adapters.LanguagesAdapter
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject


class LanguagesObserver @Inject constructor(
    private var recyclerAdapter: LanguagesAdapter,
    var applicationContext: Context
) :
    SingleObserver<AvailableLanguages> {
    val TAG = LanguagesObserver::class.java.simpleName
    var errorMessage: String? = null
    lateinit var disposable: Disposable

    override fun onSuccess(availableLanguages: AvailableLanguages) {
        val sortedLangs: LinkedHashMap<String, String> = availableLanguages.langs?.toList()
            ?.sortedBy { (_, value) -> value }
            ?.toMap() as LinkedHashMap<String, String>

        recyclerAdapter.setLanguages(sortedLangs)
        disposable.dispose()
    }

    override fun onSubscribe(disposable: Disposable) {
        this.disposable = disposable
    }

    override fun onError(e: Throwable) {
        if (recyclerAdapter.isDataAlreadyLoaded()){
            errorMessage = when (e) {
                is HttpException -> {
                    val responseCode = e.response()?.code()
                    applicationContext.getString(R.string.http_exception_message) + responseCode
                }
                is SocketTimeoutException -> {
                    applicationContext.getString(R.string.socket_timeout_exception)
                }
                is IOException -> {
                    applicationContext.getString(R.string.IOException_message)
                }
                else -> {
                    applicationContext.getString(R.string.unknown_error) + e.message
                }
            }
        }
        disposable.dispose()
    }
}