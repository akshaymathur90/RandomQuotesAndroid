package com.akshay.randomquotes.network

import com.akshay.randomquotes.models.Quote
import retrofit2.Call
import retrofit2.http.GET

interface ProgrammingQuotesApiInterface {

    @GET("/quotes/random")
    fun getRandomQuote(): Call<Quote>
}