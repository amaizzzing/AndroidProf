package com.amaizzzing.dictionary.model.datasource

import com.amaizzzing.dictionary.model.data.SearchResult
import io.reactivex.Observable

class RoomDataBaseImplementation : DataSource<List<SearchResult>> {
    override fun getData(word: String): Observable<List<SearchResult>> = Observable.fromArray()
}
