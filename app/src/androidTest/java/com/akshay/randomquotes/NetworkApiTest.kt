package com.akshay.randomquotes

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.akshay.randomquotes.network.ProgrammingQuotesApiInterface
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
class NetworkApiTest {

    private lateinit var retrofit: Retrofit
    private lateinit var apiInterface: ProgrammingQuotesApiInterface
    private val baseUrl = "https://programming-quotes-api.herokuapp.com/"
    @Before
    fun createRetrofit() {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .baseUrl(baseUrl)
            .build()

        apiInterface = retrofit.create(ProgrammingQuotesApiInterface::class.java)
    }

    @Test
    fun testApiCall() {
        val response = apiInterface.getRandomQuote().execute()
        assertEquals(200, response.code())

        val quote = response.body()
        assertNotNull(quote)
        assertNotNull(quote?.id)
        assertNotNull(quote?.en)
        assertNotNull(quote?.author)

    }
}