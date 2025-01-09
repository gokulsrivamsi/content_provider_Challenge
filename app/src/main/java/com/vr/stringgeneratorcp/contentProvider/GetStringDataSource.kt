package com.vr.stringgeneratorcp.contentProvider

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.vr.stringgeneratorcp.model.RandomText

const val Authority = "com.iav.contestdataprovider"
val string_uri = Uri.parse("content://com.iav.contestdataprovider/text")

class GetStringDataSource() {
    fun getRandomString(context: Context, limit: Int): RandomText {
        val arg = Bundle().apply {
            putInt(ContentResolver.QUERY_ARG_LIMIT, limit)
        }
        var generatedText = RandomText()
        try {
            val cursor: Cursor? = context.contentResolver.query(string_uri, null, arg, null)

            when {
                cursor == null -> {

                }

                !cursor.moveToFirst() -> {

                }

                else -> {
                    do {
                        val value = cursor.getString(cursor.getColumnIndexOrThrow("data"))
                        println(value)
                        generatedText = Gson().fromJson(value, RandomText::class.java)
                        cursor.close()
                    } while (cursor.moveToNext())
                }
            }
        } catch (e: Exception) {
            Log.d("Data Source", " $e ")
        }
        return generatedText
    }
}