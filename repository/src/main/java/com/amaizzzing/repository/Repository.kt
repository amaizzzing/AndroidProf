package com.amaizzzing.repository

interface Repository<T> {

    suspend fun getData(word: String): T
}
