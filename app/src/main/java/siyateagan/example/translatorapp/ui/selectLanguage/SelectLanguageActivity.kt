package siyateagan.example.translatorapp.ui.selectLanguage

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.databinding.ActivitySelectLanguageBinding
import siyateagan.example.translatorapp.network.ResponseStatus
import siyateagan.example.translatorapp.ui.adapters.LanguagesAdapter
import siyateagan.example.translatorapp.ui.base.BaseActivity
import javax.inject.Inject


class SelectLanguageActivity @Inject constructor() : BaseActivity() {
    val TAG = this::class.java.simpleName

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var recyclerAdapter: LanguagesAdapter

    private lateinit var selectLanguageViewModel: SelectLanguageViewModel

    private lateinit var languagesRecycler: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var binding: ActivitySelectLanguageBinding
    private val disposables = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_language)

        setSupportActionBar(binding.toolbar)
        setSearchView(binding.searchView, binding.searchDivider)

        selectLanguageViewModel =
            ViewModelProvider(this, viewModelFactory).get(SelectLanguageViewModel::class.java)

        setLanguagesRecycler()
        setSearchViewQuerySettings(binding.searchView, recyclerAdapter)

        if (recyclerAdapter.isAdapterEmpty()) loadLanguages()
        else binding.refreshLayout.isEnabled = false
    }

    private fun setLanguagesRecycler() {
        viewManager = LinearLayoutManager(this)
        val itemDecor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        languagesRecycler = binding.recyclerLanguages.apply {
            layoutManager = viewManager
            addItemDecoration(itemDecor)
        }
        languagesRecycler.adapter = recyclerAdapter
    }

    private fun loadLanguages() {
        val languagesDisposable =
            selectLanguageViewModel.getRefreshObservable().subscribe { handleResult(it) }
        disposables.add(languagesDisposable)

        initShowLoading()
    }

    private fun initShowLoading() {
        val swipeRefreshListener = setRefreshListener()
        binding.refreshLayout.post {
            binding.refreshLayout.isRefreshing = true
            swipeRefreshListener.onRefresh()
        }
    }

    private fun handleResult(result: ResponseStatus?) {
        when (result) {
            is ResponseStatus.Loading -> showLoading()
            is ResponseStatus.Success -> showLanguages()
            is ResponseStatus.Error -> showError(result.errorMessage)
        }
    }

    private fun setRefreshListener(): OnRefreshListener {
        val swipeRefreshListener = OnRefreshListener {
            selectLanguageViewModel.getLanguages()
        }
        binding.refreshLayout.setOnRefreshListener(swipeRefreshListener)
        return swipeRefreshListener
    }

    private fun showLoading() {
        if (!binding.refreshLayout.isEnabled) return
        binding.refreshLayout.isRefreshing = true
    }

    private fun showLanguages() {
        binding.refreshLayout.isRefreshing = false
        binding.refreshLayout.isEnabled = false
        binding.recyclerLanguages.visibility = View.VISIBLE
        binding.errorLayout.visibility = View.GONE
    }

    private fun showError(error: String?) {
        binding.refreshLayout.isRefreshing = false
        binding.recyclerLanguages.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
        binding.textErrorMessage.text = error
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
        recyclerAdapter.resetAdapterValues()
    }
}
