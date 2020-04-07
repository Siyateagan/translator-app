package siyateagan.example.translatorapp.ui.favorites

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.databinding.ActivityFavoritesBinding
import siyateagan.example.translatorapp.ui.base.BaseNavigationActivity
import javax.inject.Inject

class FavoritesActivity : BaseNavigationActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding: ActivityFavoritesBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_favorites)

        setSupportActionBar(binding.layoutToolbar.toolbar)
        //setSearchView(search_view, search_divider)
        setItemsIntents(binding.layoutNavigation.navView, this, this::class.java.simpleName)

        favoritesViewModel =
            ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)
    }
}
