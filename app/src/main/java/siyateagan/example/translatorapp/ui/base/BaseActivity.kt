package siyateagan.example.translatorapp.ui.base


import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.ui.adapters.LanguagesAdapter

abstract class BaseActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected fun setSearchView(searchView: SearchView?, searchDivider: View?) {
        removeSearchViewDefaultDivider(searchView)
        changeDividerStyle(searchView, searchDivider)
    }

    fun setSearchViewQuerySettings(searchView: SearchView?, adapter: LanguagesAdapter) {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { adapter.filter(it) }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }
        })
    }

    private fun removeSearchViewDefaultDivider(searchView: View?) {
        searchView?.setBackgroundColor(Color.WHITE)
    }

    private fun changeDividerStyle(searchView: SearchView?, searchDivider: View?) {
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