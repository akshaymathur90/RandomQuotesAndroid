package com.akshay.randomquotes.ui.favoritequotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akshay.randomquotes.R
import com.akshay.randomquotes.database.QuoteEntity

class FavoriteQuotesAdapter : RecyclerView.Adapter<FavoriteQuotesAdapter.ViewHolder>() {

    var favoriteQuotes = emptyList<QuoteEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_favorite_quote_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favoriteQuotes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favoriteQuotes[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvQuote: TextView = itemView.findViewById(R.id.tv_fav_quote)
        private val tvQuoteAuthor: TextView = itemView.findViewById(R.id.tv_fav_quote_author)

        fun bind(quote: QuoteEntity) {
            tvQuote.text = quote.quote
            tvQuoteAuthor.text = quote.author
        }
    }
}