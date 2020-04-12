package siyateagan.example.translatorapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import siyateagan.example.translatorapp.data.model.FavoritesEntity
import siyateagan.example.translatorapp.databinding.ItemHistoryBinding

class PairsAdapter(private val data: List<FavoritesEntity>) :
    RecyclerView.Adapter<PairsAdapter.PairsViewHolder>() {
    class PairsViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    lateinit var binding: ViewBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PairsViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PairsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PairsViewHolder, position: Int) {
        holder.binding.textCurrent.text = data[position].current
        holder.binding.textTarget.text = data[position].target
    }

    override fun getItemCount() = data.size
}
