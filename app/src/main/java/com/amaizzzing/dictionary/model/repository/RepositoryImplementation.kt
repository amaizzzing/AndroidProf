package com.amaizzzing.dictionary.model.repository

import com.amaizzzing.dictionary.model.data.SearchResult
import com.amaizzzing.dictionary.model.datasource.DataSource
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResult>>) :
    Repository<List<SearchResult>> {

    override fun getData(word: String): Observable<List<SearchResult>> {
        return dataSource.getData(word)
    }
}
