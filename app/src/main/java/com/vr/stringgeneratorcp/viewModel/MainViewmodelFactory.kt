package com.vr.stringgeneratorcp.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vr.stringgeneratorcp.contentProvider.GetStringDataSource
import com.vr.stringgeneratorcp.db.StringsDataBase
import com.vr.stringgeneratorcp.repository.RoomRepository
import com.vr.stringgeneratorcp.repository.StringGeneratorRepository
import kotlinx.coroutines.Dispatchers

class MainViewmodelFactory(
    private val application: Application,
    private val db: StringsDataBase
) :
    ViewModelProvider.AndroidViewModelFactory(application) {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val source = GetStringDataSource()
            MainViewModel(
                application,
                StringGeneratorRepository(source, Dispatchers.IO),
                RoomRepository(db)
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}