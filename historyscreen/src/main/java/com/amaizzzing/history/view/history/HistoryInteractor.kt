package com.amaizzzing.history.view.history

import com.amaizzzing.core.viewmodel.Interactor
import com.amaizzzing.model.data.AppState
import com.amaizzzing.model.data.DataModel
import com.amaizzzing.repository.Repository
import com.amaizzzing.repository.RepositoryLocal

class HistoryInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
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
