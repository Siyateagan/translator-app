package siyateagan.example.translatorapp.ui.voiceTranslation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_favorites.nav_view
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.utils.setItemsListeners

class VoiceTranslationActivity : AppCompatActivity() {
    private lateinit var voiceTranslationViewModel: VoiceTranslationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_translation)
        voiceTranslationViewModel = ViewModelProvider(this).get(VoiceTranslationViewModel::class.java)

        val navView: BottomNavigationView = nav_view
        navView.menu.getItem(1).isChecked = true

        setItemsListeners(navView, this, this::class.java.simpleName)
    }
}