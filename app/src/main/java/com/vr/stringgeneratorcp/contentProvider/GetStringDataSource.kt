package com.vr.stringgeneratorcp.contentProvider

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
            val cursor = context.contentResolver.query(string_uri, null, arg, null)
            cursor?.let {
                while (it.moveToNext()) {
                    val value = it.getString(it.getColumnIndexOrThrow("data"))
                    println(value)
                    generatedText = Gson().fromJson(value, RandomText::class.java)

                }
                it.close()
            }
        } catch (e: Exception) {
            Log.d("Data Source", " $e ")
        }
        return generatedText
    }
}