package com.amaizzzing.dictionary.model.datasource

import com.amaizzzing.dictionary.model.data.AppState
import com.amaizzzing.dictionary.model.data.DataModel
import com.amaizzzing.dictionary.room.HistoryDao
import com.amaizzzing.dictionary.utils.convertDataModelSuccessToEntity
import com.amaizzzing.dictionary.utils.mapHistoryEntityToSearchResult

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}
