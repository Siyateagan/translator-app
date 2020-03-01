package siyateagan.example.translatorapp.ui.baseActivities


import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.search_view_layout.*

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
        setSupportActionBar(toolbar)

        /**
         * Removing underline searchView if searchView exists in layout
         */
        //TODO should this be here?
        val searchHistory: View? = search_view?.findViewById(androidx.appcompat.R.id.search_plate)
        searchHistory?.setBackgroundColor(Color.WHITE)
    }

    protected abstract fun getLayoutResource(): Int
}