package com.amaizzzing.dictionary.view.main

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amaizzzing.dictionary.R
import com.amaizzzing.dictionary.model.data.DataModel
import com.amaizzzing.dictionary.model.data.SearchResult
import com.amaizzzing.dictionary.presenter.Presenter
import com.amaizzzing.dictionary.view.base.BaseActivity
import com.amaizzzing.dictionary.view.base.View
import com.amaizzzing.dictionary.view.main.adapter.MainAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : BaseActivity<DataModel>() {
    private var adapter: MainAdapter? = null
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: SearchResult) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }
    lateinit var main_activity_recyclerview: RecyclerView
    lateinit var search_fab: FloatingActionButton
    lateinit var progress_bar_horizontal: ProgressBar
    lateinit var progress_bar_round: ProgressBar
    lateinit var error_textview: TextView
    lateinit var reload_button: Button
    lateinit var success_linear_layout: FrameLayout
    lateinit var loading_frame_layout: FrameLayout
    lateinit var error_linear_layout: LinearLayout

    override fun createPresenter(): Presenter<DataModel, View> {
        return MainPresenterImpl()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_activity_recyclerview = findViewById(R.id.main_activity_recyclerview)
        search_fab = findViewById(R.id.search_fab)
        progress_bar_horizontal = findViewById(R.id.progress_bar_horizontal)
        progress_bar_round = findViewById(R.id.progress_bar_round)
        error_textview = findViewById(R.id.error_textview)
        reload_button = findViewById(R.id.reload_button)
        success_linear_layout = findViewById(R.id.success_linear_layout)
        loading_frame_layout = findViewById(R.id.loading_frame_layout)
        error_linear_layout = findViewById(R.id.error_linear_layout)

        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    presenter.getData(searchWord, true)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    override fun renderData(dataModel: DataModel) {
        when (dataModel) {
            is DataModel.Success -> {
                val searchResult = dataModel.data
                if (searchResult == null || searchResult.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        main_activity_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                        main_activity_recyclerview.adapter = MainAdapter(onListItemClickListener, searchResult)
                    } else {
                        adapter!!.setData(searchResult)
                    }
                }
            }
            is DataModel.Loading -> {
                showViewLoading()
                if (dataModel.progress != null) {
                    progress_bar_horizontal.visibility = VISIBLE
                    progress_bar_round.visibility = GONE
                    progress_bar_horizontal.progress = dataModel.progress
                } else {
                    progress_bar_horizontal.visibility = GONE
                    progress_bar_round.visibility = VISIBLE
                }
            }
            is DataModel.Error -> {
                showErrorScreen(dataModel.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        error_textview.text = error ?: getString(R.string.undefined_error)
        reload_button.setOnClickListener {
            presenter.getData("hi", true)
        }
    }

    private fun showViewSuccess() {
        success_linear_layout.visibility = VISIBLE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = GONE
    }

    private fun showViewLoading() {
        success_linear_layout.visibility = GONE
        loading_frame_layout.visibility = VISIBLE
        error_linear_layout.visibility = GONE
    }

    private fun showViewError() {
        success_linear_layout.visibility = GONE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}
