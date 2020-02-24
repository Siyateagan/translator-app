package siyateagan.example.translatorapp.ui.textTranslation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.toolbar.*
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.utils.setItemsListeners

class TextTranslationActivity : AppCompatActivity() {
    private lateinit var textTranslationViewModel: TextTranslationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translation)
        textTranslationViewModel = ViewModelProvider(this).get(TextTranslationViewModel::class.java)
        setSupportActionBar(toolbar)

        val navView: BottomNavigationView = nav_view
        navView.menu.getItem(0).isChecked = true

        setItemsListeners(navView, this, this::class.java.simpleName)
    }
}