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


class LanguagesAdapter(
    private val languagesMap: LinkedHashMap<String, String>,
    private val context: Context
) :
    RecyclerView.Adapter<LanguagesAdapter.MyViewHolder>() {
    class MyViewHolder(val item: View) : RecyclerView.ViewHolder(item)

    //TODO refactor
    private val languagesCopy: LinkedHashMap<String, String> =
        languagesMap.toMap() as LinkedHashMap<String, String>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_language, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.item.language.text = ArrayList(languagesMap.values)[position]
        holder.item.language.setOnClickListener {
            val pair = Pair(
                ArrayList(languagesMap.keys)[position],
                ArrayList(languagesMap.values)[position]
            )
            val map = HashMap<String, String>()
            map[ArrayList(languagesMap.keys)[position]] = ArrayList(languagesMap.values)[position]
            val intent = Intent().putExtra("languagePair", pair)
            (context as Activity).setResult(1, intent)
            context.finish()
        }
    }

    override fun getItemCount() = languagesMap.size

    //TODO check filterable
    fun filter(userInput: String) {
        languagesMap.clear()
        val lowerCaseInput = userInput.toLowerCase(Locale.ROOT)
        for ((index, item) in languagesCopy.values.withIndex()) {
            if (item.toLowerCase(Locale.ROOT).contains(lowerCaseInput))
                languagesMap.put(ArrayList(languagesCopy.keys)[index], item)
        }
        notifyDataSetChanged()
    }
}