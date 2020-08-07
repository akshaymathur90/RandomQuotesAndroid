package com.akshay.randomquotes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(quoteEntity: QuoteEntity)

    @Update
    fun update(quoteEntity: QuoteEntity)

    @Query("Select * FROM favorite_quotes")
    fun getAllFavoriteQuotes(): LiveData<List<QuoteEntity>>
}