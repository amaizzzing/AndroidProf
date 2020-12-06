package com.amaizzzing.dictionary.presenter

import com.amaizzzing.dictionary.model.data.DataModel
import com.amaizzzing.dictionary.view.base.View

interface Presenter<T : DataModel, V : View> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)
}
