package com.vr.stringgeneratorcp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class RandomText(
    val randomText: RandomTextData? = null
)

@Entity
data class
RandomTextData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var value: String? = null,
    var length: Int? = null,
    var created: String? = null
)