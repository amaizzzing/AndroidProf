package com.amaizzzing.repository

import com.amaizzzing.model.data.AppState

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)
}
