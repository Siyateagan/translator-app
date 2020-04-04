package siyateagan.example.translatorapp.network.observer

import android.content.Context
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import siyateagan.example.translatorapp.R
import java.io.IOException
import java.net.SocketTimeoutException

open class BaseSingleObserver<T>(private val applicationContext: Context) :
    SingleObserver<T> {
    private lateinit var disposable: Disposable

    override fun onSuccess(data: T) {
        disposable.dispose()
    }

    override fun onSubscribe(disposable: Disposable) {
        this.disposable = disposable
    }

    override fun onError(e: Throwable) {
        disposable.dispose()
    }

    protected fun getErrorMessage(e: Throwable): String = when (e) {
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