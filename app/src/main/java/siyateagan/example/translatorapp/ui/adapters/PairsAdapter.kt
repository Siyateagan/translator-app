package siyateagan.example.translatorapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import siyateagan.example.translatorapp.data.model.Dao
import siyateagan.example.translatorapp.data.model.FavoritesEntity
import siyateagan.example.translatorapp.databinding.ItemHistoryBinding

class PairsAdapter(
    private val data: MutableList<FavoritesEntity>,
    private val translationDao: Dao
) :
    RecyclerView.Adapter<PairsAdapter.PairsViewHolder>() {
    class PairsViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    val TAG = this::class.java.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PairsViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PairsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PairsViewHolder, position: Int) {
        holder.binding.textCurrent.text = data[position].current
        holder.binding.textTarget.text = data[position].target

        holder.binding.buttonFavorites.setOnClickListener {
            val favoritesEntity = FavoritesEntity(
                current = data[position].current,
                target = data[position].target
            )

            translationDao.contains(favoritesEntity.current, favoritesEntity.target)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    Completable.fromAction { translationDao.delete(result) }
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            data.removeAt(position)
                            notifyDataSetChanged()
                        }
                }
        }
    }

    override fun getItemCount() = data.size
}
