package siyateagan.example.translatorapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import siyateagan.example.translatorapp.utils.setItemsListeners

class FirstEntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_entry)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        setItemsListeners(navView, this, this::class.java.simpleName)
    }
}
