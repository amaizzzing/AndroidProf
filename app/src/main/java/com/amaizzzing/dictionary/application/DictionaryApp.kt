package com.amaizzzing.dictionary.application

import android.app.Application
import com.amaizzzing.dictionary.di.application
import com.amaizzzing.dictionary.di.mainScreen
import org.koin.core.context.startKoin

class DictionaryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(application, mainScreen))
        }
    }
}
