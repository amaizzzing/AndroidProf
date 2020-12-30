package com.amaizzzing.history.view.history

import com.amaizzzing.core.viewmodel.Interactor
import com.amaizzzing.model.data.AppState
import com.amaizzzing.model.data.dto.SearchResultDto
import com.amaizzzing.repository.Repository
import com.amaizzzing.repository.RepositoryLocal
import com.amaizzzing.dictionary.utils.mapSearchResultToResult

class HistoryInteractor(
    private val repositoryRemote: Repository<List<SearchResultDto>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResultDto>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            mapSearchResultToResult(
                if (fromRemoteSource) {
                    repositoryRemote
                } else {
                    repositoryLocal
                }.getData(word)
            )
        )
    }
}
