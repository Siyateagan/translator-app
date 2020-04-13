package siyateagan.example.translatorapp.ui.favorites

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.data.model.Dao
import siyateagan.example.translatorapp.databinding.ActivityFavoritesBinding
import siyateagan.example.translatorapp.ui.adapters.PairsAdapter
import siyateagan.example.translatorapp.ui.base.BaseNavigationActivity
import javax.inject.Inject

class FavoritesActivity : BaseNavigationActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var favoritesViewModel: FavoritesViewModel

    private val disposables = CompositeDisposable()
    lateinit var binding: ActivityFavoritesBinding

    @Inject
    lateinit var translationDao: Dao

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorites)

        setSupportActionBar(binding.layoutToolbar.toolbar)
        setItemsIntents(binding.layoutNavigation.navView, this, this::class.java.simpleName)

        favoritesViewModel =
            ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)

        binding.refreshLayout.isRefreshing = true
        binding.recyclerHistory.layoutManager = LinearLayoutManager(this)
        val dbDisposable = favoritesViewModel.getTranslationPairs()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                binding.recyclerHistory.adapter = PairsAdapter(result, translationDao)
                disableRefreshLayout()
            }

        disposables.add(dbDisposable)
    }

    private fun disableRefreshLayout() {
        binding.refreshLayout.isRefreshing = false
        binding.refreshLayout.isEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
