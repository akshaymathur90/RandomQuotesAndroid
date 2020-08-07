package com.akshay.randomquotes.di.modules

import android.app.Application
import androidx.room.Room
import com.akshay.randomquotes.database.QuoteDao
import com.akshay.randomquotes.database.QuotesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun providesQuotesDatabase(application: Application): QuotesDatabase {
        return Room.databaseBuilder(application, QuotesDatabase::class.java, "quotes-db").build()
    }

    @Singleton
    @Provides
    fun providesQuotesDao(quotesDatabase: QuotesDatabase) : QuoteDao {
        return quotesDatabase.getQuoteDao()
    }
}