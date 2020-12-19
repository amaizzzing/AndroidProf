package com.amaizzzing.history.view.history

import com.amaizzzing.core.viewmodel.Interactor
import com.amaizzzing.model.data.DataModel
import com.amaizzzing.model.data.SearchResult
import com.amaizzzing.repository.Repository
import com.amaizzzing.repository.RepositoryLocal

class HistoryInteractor(
    private val repositoryRemote: Repository<List<SearchResult>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResult>>
) : Interactor<DataModel> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): DataModel {
        return DataModel.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}
