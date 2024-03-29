package siyateagan.example.translatorapp.ui.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_language.view.*
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.data.local.StringsHelper
import siyateagan.example.translatorapp.util.ParcelablePair
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.LinkedHashMap

@Singleton
class LanguagesAdapter @Inject constructor(private val stringsHelper: StringsHelper) :
    RecyclerView.Adapter<LanguagesAdapter.MyViewHolder>() {
    class MyViewHolder(val item: View) : RecyclerView.ViewHolder(item)

    private var languagesMap = LinkedHashMap<String, String>()
    private var languagesCopy: LinkedHashMap<String, String> = LinkedHashMap(languagesMap)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_language, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.item.language.text = ArrayList(languagesMap.values)[position]

        holder.item.language.setOnClickListener {
            val context = holder.item.context
            val codeWithLanguage = ParcelablePair(
                ArrayList(languagesMap.keys)[position],
                ArrayList(languagesMap.values)[position]
            )

            returnActivityResult(codeWithLanguage, context)
        }
    }

    override fun getItemCount() = languagesMap.size

    private fun returnActivityResult(
        languageWithCode: ParcelablePair<String, String>,
        context: Context?
    ) {
        val intent = Intent().putExtra(stringsHelper.codeWithLanguage(), languageWithCode)
        (context as Activity).setResult(1, intent)
        context.finish()
    }

    fun filter(userInput: String) {
        languagesMap.clear()
        val lowerCaseInput = userInput.toLowerCase(Locale.ROOT)

        for ((index, language) in languagesCopy.values.withIndex()) {
            if (language.toLowerCase(Locale.ROOT).contains(lowerCaseInput))
                languagesMap[ArrayList(languagesCopy.keys)[index]] = language
        }
        notifyDataSetChanged()
    }

    fun resetAdapterValues() {
        languagesMap = LinkedHashMap(languagesCopy)
    }

    fun setLanguages(languages: LinkedHashMap<String, String>) {
        languagesMap.clear()
        languagesMap.putAll(languages)
        languagesCopy = LinkedHashMap(languagesMap)
        notifyDataSetChanged()
    }

    fun isAdapterEmpty() = languagesMap.isEmpty()
}