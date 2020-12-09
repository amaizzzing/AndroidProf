package com.amaizzzing.dictionary.view.main

import com.amaizzzing.dictionary.model.data.DataModel
import com.amaizzzing.dictionary.model.data.SearchResult
import com.amaizzzing.dictionary.model.repository.Repository
import com.anikin.aleksandr.simplevocabulary.viewmodel.Interactor
import io.reactivex.Observable

class MainInteractor(
    private val remoteRepository: Repository<List<SearchResult>>,
    private val localRepository: Repository<List<SearchResult>>
) : Interactor<DataModel> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<DataModel> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { DataModel.Success(it) }
        } else {
            localRepository.getData(word).map { DataModel.Success(it) }
        }
    }
}
