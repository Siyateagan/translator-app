package siyateagan.example.translatorapp.ui.favorites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.di.DaggerAppComponent
import siyateagan.example.translatorapp.utils.setItemsListeners
import javax.inject.Inject

class FavoritesActivity : AppCompatActivity() {

    @Inject
    lateinit var favoritesViewModelFactory: ViewModelFactory<FavoritesViewModel>
    lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        setSupportActionBar(toolbar)

        DaggerAppComponent.builder().build().inject(this)
        favoritesViewModel =
            ViewModelProvider(this, favoritesViewModelFactory).get(FavoritesViewModel::class.java)

        val navView: BottomNavigationView = nav_view
        navView.menu.getItem(2).isChecked = true

        setItemsListeners(navView, this, this::class.java.simpleName)
    }
}
