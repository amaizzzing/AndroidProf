package com.amaizzzing.repository

import com.amaizzzing.model.data.AppState

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)
}
