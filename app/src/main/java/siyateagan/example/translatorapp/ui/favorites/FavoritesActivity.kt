package siyateagan.example.translatorapp.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.data.local.database.FavoritesEntity
import siyateagan.example.translatorapp.databinding.ActivityFavoritesBinding
import siyateagan.example.translatorapp.ui.adapters.FavoritesAdapter
import siyateagan.example.translatorapp.ui.base.BaseNavigationActivity
import javax.inject.Inject

class FavoritesActivity : BaseNavigationActivity() {
    lateinit var binding: ActivityFavoritesBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var favoritesVM: FavoritesVM

    @Inject
    lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorites)
        favoritesVM = ViewModelProvider(this, viewModelFactory).get(FavoritesVM::class.java)

        setSupportActionBar(binding.layoutToolbar.toolbar)
        setBottomNavigation(binding.layoutNavigation.navView, this, this::class.java.simpleName)

        binding.refreshLayout.isRefreshing = true
        binding.recyclerHistory.layoutManager = LinearLayoutManager(this)

        val dbDisposable = favoritesVM.getFavorites()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { favorites ->
                setEmptyDbMessage(favorites)
                binding.recyclerHistory.adapter = favoritesAdapter
                favoritesAdapter.setData(favorites)
                disableRefreshLayout()
            }

        addDisposable(dbDisposable)
    }

    private fun setEmptyDbMessage(favorites: MutableList<FavoritesEntity>?) {
        if (favorites.isNullOrEmpty()) binding.layoutMessage.visibility = View.VISIBLE
        else binding.layoutMessage.visibility = View.GONE
    }

    private fun disableRefreshLayout() {
        binding.refreshLayout.isRefreshing = false
        binding.refreshLayout.isEnabled = false
    }
}
