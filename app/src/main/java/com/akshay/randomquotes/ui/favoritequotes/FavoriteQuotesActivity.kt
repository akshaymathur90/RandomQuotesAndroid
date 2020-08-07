package com.akshay.randomquotes.ui.favoritequotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.akshay.randomquotes.R
import com.akshay.randomquotes.RandomQuotesApp
import com.akshay.randomquotes.database.QuoteEntity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite_quotes.*
import javax.inject.Inject


class FavoriteQuotesActivity : AppCompatActivity() {

    @Inject
    lateinit var favoriteQuotesViewModelFactory: FavoriteQuotesViewModel.Factory
    private lateinit var favoriteQuotesViewModel: FavoriteQuotesViewModel
    private lateinit var adapter : FavoriteQuotesAdapter

    companion object {
        private const val TAG = "FavoriteQuotesActivity"

        fun startActivity(activity: Activity) {
            val intent = Intent(activity, FavoriteQuotesActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_quotes)

        (application as RandomQuotesApp).programmingQuotesComponent.inject(this)

        favoriteQuotesViewModel = ViewModelProvider(this, favoriteQuotesViewModelFactory).get(
            FavoriteQuotesViewModel::class.java
        )

        initList()
    }

    private fun initList() {
        adapter = FavoriteQuotesAdapter()

        list_fav_quotes.layoutManager = LinearLayoutManager(this)
        list_fav_quotes.adapter = adapter

        val itemDecoration: ItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        list_fav_quotes.addItemDecoration(itemDecoration)

        favoriteQuotesViewModel.favoriteQuotesList.observe(this, Observer { quotes ->
            quotes?.let {
                displayQuotes(it)
            }
        })
    }

    private fun displayQuotes(quotes: List<QuoteEntity>) {
        if (quotes.isNotEmpty()) {
            adapter.favoriteQuotes = quotes
            adapter.notifyDataSetChanged()
        } else {
            Snackbar.make(list_fav_quotes, getString(R.string.empty_fav_list), Snackbar.LENGTH_LONG).show()
        }
    }
}