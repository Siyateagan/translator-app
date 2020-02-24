package siyateagan.example.translatorapp.ui.favorites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_favorites.*
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.utils.setItemsListeners

class FavoritesActivity : AppCompatActivity() {
    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        val navView: BottomNavigationView = nav_view
        navView.menu.getItem(2).isChecked = true

        setItemsListeners(navView, this, this::class.java.simpleName)
    }
}