package siyateagan.example.translatorapp.util

import android.util.Log
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import retrofit2.HttpException
import siyateagan.example.translatorapp.network.AvailableLanguages
import siyateagan.example.translatorapp.ui.adapters.LanguagesAdapter
import java.io.IOException
import java.net.SocketTimeoutException


class LanguagesObserver(private val recyclerAdapter: LanguagesAdapter) :
    SingleObserver<AvailableLanguages> {
    val TAG = LanguagesObserver::class.java.simpleName

    override fun onSuccess(availableLanguages: AvailableLanguages) {
        val sortedLanguages = availableLanguages.langs?.values?.toList()?.sorted()
        val languagesWithKeys = LinkedHashMap<String, String>()

        for (index in 0 until availableLanguages.langs?.size!!) {
            languagesWithKeys[ArrayList(availableLanguages.langs!!.keys)[index]] =
                sortedLanguages?.get(index).toString()
        }

        recyclerAdapter.setLanguages(languagesWithKeys)
    }

    override fun onSubscribe(d: Disposable) {
        Log.e(TAG, "Subscribed")
    }

    override fun onError(e: Throwable) {
        when (e) {
            is HttpException -> {
                val responseBody: ResponseBody? =
                    e.response()?.errorBody()
                //view.onUnknownError(Krb5.getErrorMessage(responseBody))
                Log.e(TAG, "1st" + responseBody.toString())
                //error.code()
            }
            is SocketTimeoutException -> {
                //view.onTimeout()
                Log.e(TAG, "2nd" + e.message)
            }
            is IOException -> {
                //view.onNetworkError()
                Log.e(TAG, "3rd" + e.message)

            }
            else -> {
                //view.onUnknownError(e.getMessage())
                Log.e(TAG, "4th" + e.message)
            }
        }
    }
}