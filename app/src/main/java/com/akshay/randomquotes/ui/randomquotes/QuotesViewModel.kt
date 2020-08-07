package com.akshay.randomquotes.ui.randomquotes

import android.util.Log
import androidx.lifecycle.*
import com.akshay.randomquotes.models.Quote
import com.akshay.randomquotes.network.ResponseHandler
import com.akshay.randomquotes.repository.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuotesViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    companion object {
        private const val TAG = "QuotesViewModel"
        private const val ERROR_NO_CACHE = "Network Error & No Offline Data"
    }

    private val _quote = MutableLiveData<Quote>()
    val quote: LiveData<Quote>
        get() = _quote

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error


    class Factory @Inject constructor(var quoteRepository: QuoteRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return QuotesViewModel(quoteRepository) as T
        }
    }

    fun onFetchNewRandomQuote() {
        quoteRepository.getRandomQuote(object : ResponseHandler<Quote> {
            override fun onSuccess(data: Quote?) {
                data?.let {
                    Log.d(TAG, "Quote is = ${it.en}")
                    _quote.value = it

                }
            }

            override fun onFailure(t: Throwable?) {
                _error.value = ERROR_NO_CACHE
                Log.e(TAG, "Api Called failed and no data available in cache")
            }
        })
    }

    fun onAddToFavorite(): Boolean {
        val favQuote = _quote.value
        favQuote?.let {
            viewModelScope.launch(Dispatchers.IO) {
                quoteRepository.addToFavorites(it)
            }
            return true
        }
        return false
    }

    fun onErrorDisplayed() {
        _error.value = null
    }
}