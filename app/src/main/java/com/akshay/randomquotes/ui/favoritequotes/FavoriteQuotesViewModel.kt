package com.akshay.randomquotes.ui.favoritequotes

import androidx.lifecycle.*
import com.akshay.randomquotes.database.QuoteEntity
import com.akshay.randomquotes.repository.QuoteRepository
import javax.inject.Inject

class FavoriteQuotesViewModel(quoteRepository: QuoteRepository) : ViewModel() {

    companion object {
        private const val TAG = "FavoriteQuotesViewModel"
    }

    val favoriteQuotesList: LiveData<List<QuoteEntity>> = quoteRepository.getFavoriteQuotes()

    class Factory @Inject constructor(var quoteRepository: QuoteRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FavoriteQuotesViewModel(quoteRepository) as T
        }
    }
}