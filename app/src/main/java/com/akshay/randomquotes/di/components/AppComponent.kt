package com.akshay.randomquotes.di.components

import com.akshay.randomquotes.database.QuoteDao
import com.akshay.randomquotes.database.QuotesCache
import com.akshay.randomquotes.database.QuotesDatabase
import com.akshay.randomquotes.di.modules.AppModule
import com.akshay.randomquotes.di.modules.NetModule
import com.akshay.randomquotes.di.modules.RoomModule
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class, RoomModule::class])
interface AppComponent {
    fun quotesCache(): QuotesCache
    fun retrofit(): Retrofit
    fun okHttpClient(): OkHttpClient

    fun quoteDao(): QuoteDao
    fun quoteDatabase(): QuotesDatabase
}