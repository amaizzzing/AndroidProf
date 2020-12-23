package com.amaizzzing.dictionary.di

import androidx.room.Room
import com.amaizzzing.dictionary.model.data.DataModel
import com.amaizzzing.dictionary.model.datasource.RetrofitImplementation
import com.amaizzzing.dictionary.model.datasource.RoomDataBaseImplementation
import com.amaizzzing.dictionary.model.repository.Repository
import com.amaizzzing.dictionary.model.repository.RepositoryImplementation
import com.amaizzzing.dictionary.model.repository.RepositoryImplementationLocal
import com.amaizzzing.dictionary.model.repository.RepositoryLocal
import com.amaizzzing.dictionary.view.main.MainInteractor
import com.amaizzzing.dictionary.view.main.MainViewModel
import com.amaizzzing.model.room.HistoryDataBase
import com.amaizzzing.repository.*
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, mainScreen))
}

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }
}
