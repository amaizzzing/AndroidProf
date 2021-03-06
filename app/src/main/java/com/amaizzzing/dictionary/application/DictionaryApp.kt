package com.amaizzzing.dictionary.application

import android.app.Application
import com.amaizzzing.dictionary.di.application
import com.amaizzzing.dictionary.di.historyScreen
import com.amaizzzing.dictionary.di.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DictionaryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, historyScreen))
        }
    }
}
