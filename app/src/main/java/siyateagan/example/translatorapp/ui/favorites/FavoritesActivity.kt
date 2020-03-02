package siyateagan.example.translatorapp.ui.favorites

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.bottom_navigation_layout.*
import kotlinx.android.synthetic.main.layout_toolbar.toolbar
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.di.DaggerAppComponent
import siyateagan.example.translatorapp.ui.baseActivities.BaseNavigationActivity
import javax.inject.Inject

class FavoritesActivity : BaseNavigationActivity() {

    @Inject
    lateinit var favoritesViewModelFactory: ViewModelFactory<FavoritesViewModel>
    lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        setSupportActionBar(toolbar)
        removeSearchViewUnderline()
        setItemsIntents(nav_view, this, this::class.java.simpleName)

        DaggerAppComponent.builder().build().inject(this)
        favoritesViewModel =
            ViewModelProvider(this, favoritesViewModelFactory).get(FavoritesViewModel::class.java)
    }
}
