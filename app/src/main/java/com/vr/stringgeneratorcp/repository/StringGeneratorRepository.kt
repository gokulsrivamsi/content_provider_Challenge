package com.vr.stringgeneratorcp.repository

import android.content.Context
import com.vr.stringgeneratorcp.contentProvider.GetStringDataSource
import com.vr.stringgeneratorcp.model.RandomText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class StringGeneratorRepository(
    private val source: GetStringDataSource,
    private val myDispatcher: CoroutineDispatcher
) {
    suspend fun generatorString(context: Context, limit: Int): RandomText {
        return withContext(myDispatcher) {
            source.getRandomString(context, limit)
        }
    }
}