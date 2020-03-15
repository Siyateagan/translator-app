package siyateagan.example.translatorapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_language.view.*
import siyateagan.example.translatorapp.R
import java.util.*
import kotlin.collections.ArrayList

class LanguagesAdapter(private val languagesList: List<String>) :
    RecyclerView.Adapter<LanguagesAdapter.MyViewHolder>() {
    class MyViewHolder(val item: View) : RecyclerView.ViewHolder(item)

    private val languagesCopy: ArrayList<String> = ArrayList()

    init {
        languagesCopy.addAll(languagesList)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_language, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.item.language.text = languagesList[position]
    }

    override fun getItemCount() = languagesList.size

    //TODO check filterable
    fun filter(userInput: String) {
        (languagesList as ArrayList<String>).clear()
        val lowerCaseInput = userInput.toLowerCase(Locale.ROOT)
        for (item in languagesCopy) {
            if (item.toLowerCase(Locale.ROOT).contains(lowerCaseInput))
                languagesList.add(item)
        }
        notifyDataSetChanged()
    }
}