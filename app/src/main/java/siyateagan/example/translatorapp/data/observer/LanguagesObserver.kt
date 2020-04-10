package siyateagan.example.translatorapp.data.observer

import android.content.Context
import siyateagan.example.translatorapp.data.model.AvailableLanguages
import siyateagan.example.translatorapp.data.local.ResponseStatus
import siyateagan.example.translatorapp.ui.adapters.LanguagesAdapter
import siyateagan.example.translatorapp.util.ObservableVariable
import javax.inject.Inject

class LanguagesObserver @Inject constructor(
    private var recyclerAdapter: LanguagesAdapter,
    var applicationContext: Context
) :
    BaseSingleObserver<AvailableLanguages>(applicationContext) {
    var isRefreshing: ObservableVariable<ResponseStatus> =
        ObservableVariable(ResponseStatus.Loading)

    override fun onSuccess(data: AvailableLanguages) {
        super.onSuccess(data)
        val sortedLangs: LinkedHashMap<String, String> = sortLanguages(data)
        recyclerAdapter.setLanguages(sortedLangs)
        isRefreshing.value = ResponseStatus.Success
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        isRefreshing.value = ResponseStatus.Error(getErrorMessage(e))
    }

    private fun sortLanguages(availableLanguages: AvailableLanguages): LinkedHashMap<String, String> =
        availableLanguages.langs?.toList()
            ?.sortedBy { (_, value) -> value }
            ?.toMap() as LinkedHashMap<String, String>
}