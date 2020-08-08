package com.akshay.randomquotes

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.akshay.randomquotes.database.QuoteDao
import com.akshay.randomquotes.database.QuoteEntity
import com.akshay.randomquotes.database.QuotesDatabase
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class QuotesDatabaseTest {
    private lateinit var quoteDao: QuoteDao
    private lateinit var db: QuotesDatabase


    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, QuotesDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        quoteDao = db.getQuoteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetQuote() {
        val quote = QuoteEntity("1","Testing is Important", "Everyone")
        quoteDao.insert(quote)
        val favQuote = quoteDao.getQuote("1")

        assertEquals(true, favQuote != null)

        assertEquals("1", favQuote?.id)
        assertEquals("Testing is Important", favQuote?.quote)
        assertEquals("Everyone", favQuote?.author)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.akshay.randomquotes", appContext.packageName)
    }
}