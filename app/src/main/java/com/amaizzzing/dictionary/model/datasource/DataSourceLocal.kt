package com.amaizzzing.dictionary.model.datasource

import com.amaizzzing.dictionary.model.data.AppState

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)
}
