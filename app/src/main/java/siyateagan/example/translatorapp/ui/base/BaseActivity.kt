package siyateagan.example.translatorapp.ui.base


import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.search_view_layout.*

abstract class BaseActivity : AppCompatActivity() {
    protected fun removeSearchViewUnderline() {
        val searchView: View? = search_view?.findViewById(androidx.appcompat.R.id.search_plate)
        searchView?.setBackgroundColor(Color.WHITE)
    }
}