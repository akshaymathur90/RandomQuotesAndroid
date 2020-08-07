package com.akshay.randomquotes.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [QuoteEntity::class], version = 1, exportSchema = false)
abstract class QuotesDatabase: RoomDatabase() {

    abstract fun getQuoteDao(): QuoteDao
}