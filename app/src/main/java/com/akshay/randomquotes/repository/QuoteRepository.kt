package com.akshay.randomquotes.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.akshay.randomquotes.database.QuoteDao
import com.akshay.randomquotes.database.QuoteEntity
import com.akshay.randomquotes.database.QuotesCache
import com.akshay.randomquotes.models.Quote
import com.akshay.randomquotes.network.ProgrammingQuotesApiInterface
import com.akshay.randomquotes.network.ResponseHandler
import com.akshay.randomquotes.utils.toQuoteEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

interface QuoteRepository {
    fun getRandomQuote(responseHandler: ResponseHandler<Quote>)
    fun getFavoriteQuotes(): LiveData<List<QuoteEntity>>
    suspend fun addToFavorites(quote: Quote)
}

class QuoteRepositoryImpl : QuoteRepository {

    private var quoteDao: QuoteDao
    private var programmingQuotesApiInterface: ProgrammingQuotesApiInterface
    private var quotesCache: QuotesCache

    companion object {
        private const val TAG = "QuoteRepositoryImpl"
    }

    @Inject
    constructor(
        quoteDao: QuoteDao,
        programmingQuotesApiInterface: ProgrammingQuotesApiInterface,
        quotesCache: QuotesCache
    ) {
        this.quoteDao = quoteDao
        this.programmingQuotesApiInterface = programmingQuotesApiInterface
        this.quotesCache = quotesCache
    }

    override fun getRandomQuote(responseHandler: ResponseHandler<Quote>) {
        Log.d(TAG, "Getting Random Quote")
        programmingQuotesApiInterface.getRandomQuote().enqueue(object : Callback<Quote> {
            override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
                Log.d(TAG, "Response code = ${response.code()}")
                if (response.isSuccessful) {
                    val quote = response.body()
                    Log.i(TAG, quote.toString())
                    if (quote != null) {
                        quotesCache.cacheQuote(quote)
                        responseHandler.onSuccess(quote)
                        return
                    }
                }
                Log.d(TAG, "Response unsuccessful = ${response.errorBody()}")
                handleFailure(responseHandler)

            }

            override fun onFailure(call: Call<Quote>, t: Throwable) {
                Log.i(TAG, "Error", t)
                handleFailure(responseHandler)
            }
        })
    }

    private fun handleFailure(responseHandler: ResponseHandler<Quote>) {
        val cacheQuote = quotesCache.getCachedQuote()
        if (cacheQuote != null) {
            Log.d(TAG, "Responding from Cache")
            responseHandler.onSuccess(cacheQuote)
        } else {
            Log.e(TAG, "Cache Empty")
            responseHandler.onFailure()
        }
    }

    override fun getFavoriteQuotes(): LiveData<List<QuoteEntity>> {
        return quoteDao.getAllFavoriteQuotes()
    }

    override suspend fun addToFavorites(quote: Quote) {
        quoteDao.insert(quote.toQuoteEntity())
    }
}