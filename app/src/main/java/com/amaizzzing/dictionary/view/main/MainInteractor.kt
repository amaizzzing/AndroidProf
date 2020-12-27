package com.amaizzzing.dictionary.view.main

import com.amaizzzing.core.viewmodel.Interactor
import com.amaizzzing.model.data.AppState
import com.amaizzzing.model.data.dto.SearchResultDto
import com.amaizzzing.repository.Repository
import com.amaizzzing.repository.RepositoryLocal
import com.amaizzzing.dictionary.utils.mapSearchResultToResult

class MainInteractor(
    private val repositoryRemote: Repository<List<SearchResultDto>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResultDto>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(mapSearchResultToResult(repositoryRemote.getData(word)))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = AppState.Success(mapSearchResultToResult(repositoryLocal.getData(word)))
        }
        return appState
    }
}
