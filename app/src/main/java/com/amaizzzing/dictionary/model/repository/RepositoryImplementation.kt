package com.amaizzzing.dictionary.model.repository

import com.amaizzzing.dictionary.model.data.DataModel
import com.amaizzzing.dictionary.model.datasource.DataSource
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}
