package com.akshay.randomquotes.ui.randomquotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.akshay.randomquotes.R
import com.akshay.randomquotes.RandomQuotesApp
import com.akshay.randomquotes.models.Quote
import com.akshay.randomquotes.ui.favoritequotes.FavoriteQuotesActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_random_quote.*
import javax.inject.Inject

class RandomQuoteActivity : AppCompatActivity() {

    @Inject
    lateinit var quotesViewModelFactory: QuotesViewModel.Factory

    private lateinit var quotesViewModel: QuotesViewModel

    companion object {
        private const val TAG = "RandomQuoteActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_quote)
        (application as RandomQuotesApp).programmingQuotesComponent.inject(this)

        quotesViewModel =
            ViewModelProvider(this, quotesViewModelFactory).get(QuotesViewModel::class.java)

        init()
    }

    private fun init() {
        quotesViewModel.quote.observe(this, Observer {
            displayQuote(it)
        })

        quotesViewModel.error.observe(this, Observer { error ->
            error?.let {
                displayError(it)
            }
        })

        btn_fetch_quote.setOnClickListener {
            fetchRandomQuote()
        }
        btn_add_fav.setOnClickListener {
            addToFavorites()
        }
        btn_view_fav.setOnClickListener {
            onViewFavorites()
        }
    }

    private fun onViewFavorites() {
        FavoriteQuotesActivity.startActivity(this)
    }

    private fun displayQuote(quote: Quote) {
        tv_quote.text = quote.en
        tv_author.text = quote.author
    }

    private fun fetchRandomQuote() {
        quotesViewModel.onFetchNewRandomQuote()
        displayProgress()
    }

    private fun addToFavorites() {
        val success = quotesViewModel.onAddToFavorite()
        val msg = if (success) {
            getString(R.string.added_to_fav_list)
        } else {
            getString(R.string.nothing_to_add)
        }
        Snackbar.make(tv_quote, msg, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun displayError(errorString: String) {
        Snackbar.make(tv_quote, errorString, Snackbar.LENGTH_SHORT).show()
        tv_quote.text = getString(R.string.no_offline_data)
        tv_author.text = getString(R.string.no_offline_data)
        quotesViewModel.onErrorDisplayed()
    }

    private fun displayProgress() {
        tv_quote.text = getString(R.string.loading)
        tv_author.text = getString(R.string.loading)
    }
}