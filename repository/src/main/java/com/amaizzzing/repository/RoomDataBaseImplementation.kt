package com.amaizzzing.repository

import com.amaizzzing.model.data.AppState
import com.amaizzzing.model.data.dto.SearchResultDto
import com.amaizzzing.model.room.HistoryDao

class RoomDataBaseImplementation(private val historyDao: HistoryDao) : DataSourceLocal<List<SearchResultDto>> {

    override suspend fun getData(word: String): List<SearchResultDto> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}
