package com.akshay.randomquotes.utils

import com.akshay.randomquotes.database.QuoteEntity
import com.akshay.randomquotes.models.Quote

fun Quote.toQuoteEntity(): QuoteEntity {
    return QuoteEntity(id, en, author)
}

fun QuoteEntity.toQuote(): Quote {
    return Quote(id, quote, author)
}