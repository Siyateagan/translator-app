package siyateagan.example.translatorapp.ui.base


import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import siyateagan.example.translatorapp.R

abstract class BaseActivity : AppCompatActivity() {

    protected fun setSearchView(searchView: SearchView?, searchDivider: View?){
        removeSearchViewDefaultDivider(searchView)
        setSearchViewQuerySettings(searchView)
        changeDividerStyle(searchView, searchDivider)
    }

    private fun removeSearchViewDefaultDivider(searchView: View?) {
        searchView?.setBackgroundColor(Color.WHITE)
    }

    private fun setSearchViewQuerySettings(searchView: SearchView?){
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }
        })
    }

    private fun changeDividerStyle(searchView: SearchView?, searchDivider: View?){
        val layoutParams = searchDivider?.layoutParams
        searchView?.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                searchDivider?.setBackgroundResource(R.color.colorAccent)
                layoutParams?.height = resources.getDimension(R.dimen.divider_height_2dp).toInt()
                searchDivider?.layoutParams = layoutParams
            } else {
                searchDivider?.setBackgroundResource(R.color.gray)
                layoutParams?.height = resources.getDimension(R.dimen.divider_height_1dp).toInt()
                searchDivider?.layoutParams = layoutParams
            }
        }
    }
}