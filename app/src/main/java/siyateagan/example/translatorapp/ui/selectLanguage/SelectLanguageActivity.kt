package siyateagan.example.translatorapp.ui.selectLanguage

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import siyateagan.example.translatorapp.R
import siyateagan.example.translatorapp.data.local.ResponseStatus
import siyateagan.example.translatorapp.databinding.ActivitySelectLanguageBinding
import siyateagan.example.translatorapp.ui.adapters.LanguagesAdapter
import siyateagan.example.translatorapp.ui.base.BaseActivity
import siyateagan.example.translatorapp.ui.base.interfaces.OnRetryClick
import javax.inject.Inject


class SelectLanguageActivity @Inject constructor() : BaseActivity(), OnRetryClick {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var selectLanguageVM: SelectLanguageVM

    private lateinit var binding: ActivitySelectLanguageBinding

    @Inject
    lateinit var recyclerAdapter: LanguagesAdapter

    private lateinit var swipeRefreshListener: OnRefreshListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_language)
        selectLanguageVM =
            ViewModelProvider(this, viewModelFactory).get(SelectLanguageVM::class.java)

        setSupportActionBar(binding.layoutToolbar.toolbar)
        setSearchView(binding.layoutSearchView.searchView, binding.layoutSearchView.searchDivider)
        setSearchViewQuerySettings(binding.layoutSearchView.searchView, recyclerAdapter)

        setLanguagesRecycler()

        if (recyclerAdapter.isAdapterEmpty()) loadLanguages()
        else binding.refreshLayout.isEnabled = false

        binding.errorLayout.listener = this
        swipeRefreshListener = setRefreshListener()
    }

    override fun onRetryClick() {
        binding.refreshLayout.isRefreshing = true
        swipeRefreshListener.onRefresh()
    }

    private fun setLanguagesRecycler() {
        val viewManager = LinearLayoutManager(this)
        val itemDecor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        binding.recyclerLanguages.apply {
            layoutManager = viewManager
            addItemDecoration(itemDecor)
            adapter = recyclerAdapter
        }
    }

    private fun loadLanguages() {
        val languagesDisposable =
            selectLanguageVM.getRefreshObservable().subscribe { handleResult(it) }
        addDisposable(languagesDisposable)
        initRefresh()
    }

    private fun initRefresh() {
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
            selectLanguageVM.getLanguages()
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
        binding.errorLayout.errorLayout.visibility = View.GONE
    }

    private fun showError(error: String?) {
        binding.refreshLayout.isRefreshing = false
        binding.recyclerLanguages.visibility = View.GONE
        binding.errorLayout.errorLayout.visibility = View.VISIBLE
        binding.errorLayout.textErrorMessage.text = error
    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerAdapter.resetAdapterValues()
    }
}
