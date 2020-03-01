package siyateagan.example.translatorapp.ui.favorites

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
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

        DaggerAppComponent.builder().build().inject(this)
        favoritesViewModel =
            ViewModelProvider(this, favoritesViewModelFactory).get(FavoritesViewModel::class.java)
    }

    override fun getLayoutResource() = R.layout.activity_favorites
    override fun getClassName() = this::class.java.simpleName
}
