package com.akshay.randomquotes

import android.app.Application
import com.akshay.randomquotes.di.components.*
import com.akshay.randomquotes.di.modules.AppModule
import com.akshay.randomquotes.di.modules.NetModule
import com.akshay.randomquotes.di.modules.RoomModule
import com.facebook.stetho.Stetho

class RandomQuotesApp : Application() {

    lateinit var programmingQuotesComponent: ProgrammingQuotesComponent
    private val baseUrl = "https://programming-quotes-api.herokuapp.com/"


    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule(baseUrl))
            .roomModule(RoomModule())
            .build()


        programmingQuotesComponent = DaggerProgrammingQuotesComponent.builder()
            .appComponent(appComponent)
            .build()
    }
}