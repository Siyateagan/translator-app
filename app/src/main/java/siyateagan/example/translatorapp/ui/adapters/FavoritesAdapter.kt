package siyateagan.example.translatorapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import siyateagan.example.translatorapp.data.model.Dao
import siyateagan.example.translatorapp.data.model.FavoritesEntity
import siyateagan.example.translatorapp.databinding.ItemHistoryBinding
import javax.inject.Inject

class FavoritesAdapter @Inject constructor(private val translationDao: Dao) :
    RecyclerView.Adapter<FavoritesAdapter.PairsViewHolder>() {
    class PairsViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var favoritesEntities: MutableList<FavoritesEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PairsViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PairsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PairsViewHolder, position: Int) {
        holder.binding.textCurrent.text = favoritesEntities[position].current
        holder.binding.textTarget.text = favoritesEntities[position].target

        holder.binding.buttonFavorites.setOnClickListener {
            Completable.fromAction { translationDao.delete(favoritesEntities[position]) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    favoritesEntities.removeAt(position)
                    notifyDataSetChanged()
                }
        }
    }

    override fun getItemCount() = favoritesEntities.size

    fun setData(data: MutableList<FavoritesEntity>) {
        favoritesEntities = data
    }
}
