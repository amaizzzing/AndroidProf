package com.amaizzzing.dictionary.view.main

import com.amaizzzing.dictionary.di.NAME_LOCAL
import com.amaizzzing.dictionary.di.NAME_REMOTE
import com.amaizzzing.dictionary.model.data.AppState
import com.amaizzzing.dictionary.model.data.DataModel
import com.amaizzzing.dictionary.model.repository.Repository
import com.amaizzzing.dictionary.viewmodel.Interactor
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
        @Named(NAME_REMOTE) val repositoryRemote: Repository<List<DataModel>>,
        @Named(NAME_LOCAL) val repositoryLocal: Repository<List<DataModel>>
) : Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            repositoryRemote
        } else {
            repositoryLocal
        }.getData(word).map { AppState.Success(it) }
    }
}
