package com.app.soulstudio.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.soulstudio.common.SoulStudioApplication
import com.app.soulstudio.databinding.ActivityMainBinding
import com.app.soulstudio.model.dataclass.Items
import com.app.soulstudio.utility.NetworkUtility
import com.app.soulstudio.view.adapter.BookAdapter
import com.app.soulstudio.view.callbacks.BookCallbacks
import com.app.soulstudio.viewmodel.BookViewModel
import com.app.soulstudio.viewmodel.BookViewModelFactory


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var activityMainBinding: ActivityMainBinding

    private val bookViewModel: BookViewModel by viewModels {
        BookViewModelFactory((application as SoulStudioApplication).bookRepository)
    }

    private var itemsList: MutableList<Items> = mutableListOf()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var bookAdapter: BookAdapter

    private var apiQueryBookName = "flowers"
    private var apiMaxResult = 20
    private var apiStartIndex = 0

    private var isLoadMoreData = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        init()


    }


    private fun init() {

        linearLayoutManager = LinearLayoutManager(this)
        activityMainBinding.rvBookItem.layoutManager = linearLayoutManager

        bookAdapter = BookAdapter(object : BookCallbacks.Companion.IBookCallbacks {
            override fun onBookClick(position: Int, items: Items) {
                val intent = Intent(this@MainActivity, BookDetailsActivity::class.java)
                intent.putExtra("itemData", items)
                startActivity(intent)
            }
        })

        activityMainBinding.rvBookItem.adapter = bookAdapter


        activityMainBinding.rvBookItem.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == linearLayoutManager.itemCount - 1) {
                    if (isLoadMoreData) {
                        activityMainBinding.pbLoading.visibility = View.VISIBLE
                        isLoadMoreData = false
                        bookViewModel.getBook(
                            apiQueryBookName,
                            apiStartIndex.toString(),
                            apiMaxResult.toString()
                        )
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        observeBookViewModel()
        bookViewModel.getBook(
            apiQueryBookName,
            apiStartIndex.toString(),
            apiMaxResult.toString()
        )

    }

    //observe view model for book
    fun observeBookViewModel() {
        bookViewModel.bookResponse.observe(this, Observer {

            if (it != null && it.items != null) {
                itemsList.addAll(it.items)
                bookAdapter.submitList(ArrayList(itemsList))

                apiStartIndex += apiMaxResult
                apiMaxResult = 40

                isLoadMoreData = true

                activityMainBinding.lyData.visibility = View.VISIBLE
                activityMainBinding.pbLoading.visibility = View.GONE

                val params = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                params.addRule(RelativeLayout.CENTER_HORIZONTAL)
                activityMainBinding.pbLoading.layoutParams = params
                activityMainBinding.tvMessage.layoutParams = params
            } else {
                activityMainBinding.lyData.visibility = View.VISIBLE
                activityMainBinding.pbLoading.visibility = View.GONE
                activityMainBinding.tvMessage.visibility = View.VISIBLE

                activityMainBinding.tvMessage.text =
                    "Data finished."
            }


        })

        bookViewModel.errorResponse.observe(this, Observer {
            activityMainBinding.pbLoading.visibility = View.GONE

            if (NetworkUtility.isInternetAvailable()) {
                activityMainBinding.tvMessage.text = "Server error, pleas try again."
                activityMainBinding.tvMessage.visibility = View.VISIBLE

            } else {
                activityMainBinding.tvMessage.text =
                    "Please check your internet connection, and try again."
                activityMainBinding.tvMessage.visibility = View.VISIBLE

                activityMainBinding.pbLoading.visibility = View.GONE
            }
        })
    }
}