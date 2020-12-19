package com.amaizzzing.dictionary.view.main

import com.amaizzzing.dictionary.model.data.AppState
import com.amaizzzing.dictionary.model.data.DataModel
import com.amaizzzing.dictionary.model.repository.Repository
import com.amaizzzing.dictionary.viewmodel.Interactor

class MainInteractor(
        private val repositoryRemote: Repository<List<DataModel>>,
        private val repositoryLocal: Repository<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}
