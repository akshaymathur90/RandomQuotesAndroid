package com.akshay.randomquotes.database

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.akshay.randomquotes.models.Quote
import javax.inject.Inject

class QuotesCache @Inject constructor(application: Application) {

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("quote_cache", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_QUOTE = "KEY_QUOTE"
        private const val KEY_AUTHOR = "KEY_AUTHOR"
        private const val KEY_ID = "KEY_ID"
    }

    fun getCachedQuote(): Quote? {

        val quoteId = sharedPreferences.getString(KEY_ID, null)
        val quoteString = sharedPreferences.getString(KEY_QUOTE, null)
        val quoteAuthor = sharedPreferences.getString(KEY_AUTHOR, null)

        if (quoteId == null || quoteString == null || quoteAuthor == null) {
            return null
        }

        return Quote(quoteId, quoteString, quoteAuthor)
    }

    fun cacheQuote(quote: Quote) {
        sharedPreferences.edit()
            .putString(KEY_ID, quote.id)
            .putString(KEY_AUTHOR, quote.author)
            .putString(KEY_QUOTE, quote.en)
            .apply()
    }
}