package siyateagan.example.translatorapp.util

import android.content.Context
import android.util.Log
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import retrofit2.HttpException
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.network.AvailableLanguages
import siyateagan.example.translatorapp.ui.adapters.LanguagesAdapter
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject


class LanguagesObserver @Inject constructor(
    private val recyclerAdapter: LanguagesAdapter,
    var applicationContext: Context
) :
    SingleObserver<AvailableLanguages> {
    val TAG = LanguagesObserver::class.java.simpleName

    override fun onSuccess(availableLanguages: AvailableLanguages) {
        val sortedLanguages = availableLanguages.langs?.values?.toList()?.sorted()
        val languagesWithKeys = LinkedHashMap<String, String>()

        availableLanguages.langs?.let {
            for (index in 0 until it.keys.size) {
                languagesWithKeys[ArrayList(it.keys)[index]] =
                    sortedLanguages?.get(index).toString()
            }

            recyclerAdapter.setLanguages(languagesWithKeys)
        }
    }

    override fun onSubscribe(d: Disposable) {
        Log.e(TAG, "Subscribed")
    }

    override fun onError(e: Throwable) {
        when (e) {
            is HttpException -> {
                val responseBody: ResponseBody? = e.response()?.errorBody()
                Log.e(TAG, applicationContext.getString(R.string.http_exception_message) + responseBody)
            }
            is SocketTimeoutException -> {
                Log.e(TAG, applicationContext.getString(R.string.socket_timeout_exception) + e.message)
            }
            is IOException -> {
                Log.e(TAG, applicationContext.getString(R.string.IOException_message) + e.message)
            }
            else -> {
                Log.e(TAG,  applicationContext.getString(R.string.unknown_error)+ e.message)
            }
        }
    }
}