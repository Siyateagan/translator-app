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
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.LinkedHashMap

@Singleton
class LanguagesAdapter @Inject constructor() :
    RecyclerView.Adapter<LanguagesAdapter.MyViewHolder>() {
    class MyViewHolder(val item: View) : RecyclerView.ViewHolder(item)

    private val languagesMap = LinkedHashMap<String, String>()
    private var languagesCopy: LinkedHashMap<String, String> = LinkedHashMap(languagesMap)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_language, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.item.context

        holder.item.language.text = ArrayList(languagesMap.values)[position]
        holder.item.language.setOnClickListener {
            val languageWithCode = Pair(
                ArrayList(languagesMap.keys)[position],
                ArrayList(languagesMap.values)[position]
            )
            val intent = Intent().putExtra("languageWithCode", languageWithCode)
            (context as Activity).setResult(1, intent)
            context.finish()
        }
    }

    override fun getItemCount() = languagesMap.size

    //TODO check filterable
    fun filter(userInput: String) {
        languagesMap.clear()
        val lowerCaseInput = userInput.toLowerCase(Locale.ROOT)
        for ((index, language) in languagesCopy.values.withIndex()) {
            if (language.toLowerCase(Locale.ROOT).contains(lowerCaseInput))
                languagesMap[ArrayList(languagesCopy.keys)[index]] = language
        }
        notifyDataSetChanged()
    }

    fun setLanguages(languages: LinkedHashMap<String, String>) {
        languagesMap.clear()
        languagesMap.putAll(languages)
        languagesCopy = LinkedHashMap(languagesMap)
        notifyDataSetChanged()
    }

    fun isDataAlreadyLoaded() = languagesMap.isEmpty()
}