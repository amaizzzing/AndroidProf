package com.amaizzzing.dictionary.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import com.amaizzzing.core.BaseActivity
import com.amaizzzing.dictionary.R
import com.amaizzzing.dictionary.di.injectDependencies
import com.amaizzzing.dictionary.model.data.DataModel
import com.amaizzzing.dictionary.utils.convertMeaningsToString
import com.amaizzzing.dictionary.view.descriptionscreen.DescriptionActivity
import com.amaizzzing.dictionary.view.main.adapter.MainAdapter
import com.amaizzzing.model.data.AppState
import com.amaizzzing.translator.view.main.SearchDialogFragment
import com.amaizzzing.utils.network.isOnline
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
private const val HISTORY_ACTIVITY_PATH = "com.amaizzzing.history.view.history.HistoryActivity"
private const val HISTORY_ACTIVITY_FEATURE_NAME = "historyScreen"

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    override lateinit var model: MainViewModel
    private lateinit var splitInstallManager: SplitInstallManager

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }
    private val fabClickListener: View.OnClickListener =
        View.OnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                startActivity(
                    DescriptionActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        convertMeaningsToString(data.meanings!!),
                        data.meanings!![0].imageUrl
                    )
                )
            }
        }
    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                isNetworkAvailable = isOnline(applicationContext)
                if (isNetworkAvailable) {
                    model.getData(searchWord, isNetworkAvailable)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniViewModel()
        initViews()
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
                val request =
                    SplitInstallRequest
                        .newBuilder()
                        .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
                        .build()

                splitInstallManager
                    .startInstall(request)
                    .addOnSuccessListener {
                        val intent = Intent().setClassName(packageName, HISTORY_ACTIVITY_PATH)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            applicationContext,
                            "Couldn't download feature: " + it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun iniViewModel() {
        check(main_activity_recyclerview.adapter == null) { "The ViewModel should be initialised first" }
        injectDependencies()
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })
    }

    private fun initViews() {
        search_fab.setOnClickListener(fabClickListener)
        main_activity_recyclerview.adapter = adapter
    }
}
