package com.akshay.randomquotes.di.modules

import com.akshay.randomquotes.database.QuoteDao
import com.akshay.randomquotes.database.QuotesCache
import com.akshay.randomquotes.di.scopes.UserScope
import com.akshay.randomquotes.network.ProgrammingQuotesApiInterface
import com.akshay.randomquotes.repository.QuoteRepository
import com.akshay.randomquotes.repository.QuoteRepositoryImpl
import com.akshay.randomquotes.ui.favoritequotes.FavoriteQuotesViewModel
import com.akshay.randomquotes.ui.randomquotes.QuotesViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ProgrammingQuotesModule {

    @Provides
    @UserScope
    fun providesProgrammingQuotesInterface(retrofit: Retrofit): ProgrammingQuotesApiInterface {
        return retrofit.create(ProgrammingQuotesApiInterface::class.java)
    }

    @Provides
    @UserScope
    fun providesQuotesRepository(quoteDao: QuoteDao,
                                 programmingQuotesApiInterface: ProgrammingQuotesApiInterface,
                                 quotesCache: QuotesCache): QuoteRepository {
        return QuoteRepositoryImpl(quoteDao, programmingQuotesApiInterface, quotesCache)
    }

    @Provides
    @UserScope
    fun providesQuotesViewModelFactory(quotesRepository: QuoteRepository): QuotesViewModel.Factory {
        return QuotesViewModel.Factory(quotesRepository)
    }

    @Provides
    @UserScope
    fun providesFavoriteQuotesViewModelFactory(quotesRepository: QuoteRepository): FavoriteQuotesViewModel.Factory {
        return FavoriteQuotesViewModel.Factory(quotesRepository)
    }
}