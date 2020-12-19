package com.amaizzzing.dictionary.model.repository

import com.amaizzzing.dictionary.model.data.AppState

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)
}
