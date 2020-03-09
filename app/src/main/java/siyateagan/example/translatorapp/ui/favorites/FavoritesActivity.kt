package siyateagan.example.translatorapp.ui.favorites

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.bottom_navigation_layout.*
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import kotlinx.android.synthetic.main.search_view_layout.*
import siyateagan.example.translatorapp.R

import siyateagan.example.translatorapp.ui.base.BaseNavigationActivity
import javax.inject.Inject

class FavoritesActivity : BaseNavigationActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        setSupportActionBar(toolbar)
        setSearchView(search_view, search_divider)
        setItemsIntents(nav_view, this, this::class.java.simpleName)

        favoritesViewModel =
            ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)
    }
}
