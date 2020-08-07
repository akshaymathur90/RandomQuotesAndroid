package com.akshay.randomquotes.di.components

import com.akshay.randomquotes.ui.randomquotes.RandomQuoteActivity
import com.akshay.randomquotes.di.modules.ProgrammingQuotesModule
import com.akshay.randomquotes.di.scopes.UserScope
import com.akshay.randomquotes.ui.favoritequotes.FavoriteQuotesActivity
import dagger.Component

@UserScope
@Component(dependencies = [AppComponent::class], modules = [ProgrammingQuotesModule::class])
interface ProgrammingQuotesComponent {

    fun inject(activity: RandomQuoteActivity)
    fun inject(activity: FavoriteQuotesActivity)
}