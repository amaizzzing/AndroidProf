package com.amaizzzing.dictionary.model.repository

import com.amaizzzing.dictionary.model.data.AppState
import com.amaizzzing.dictionary.model.data.DataModel
import com.amaizzzing.dictionary.model.datasource.DataSourceLocal

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<DataModel>>) :
    RepositoryLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}
