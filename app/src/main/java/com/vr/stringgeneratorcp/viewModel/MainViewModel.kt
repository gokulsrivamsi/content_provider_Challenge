package com.vr.stringgeneratorcp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vr.stringgeneratorcp.model.RandomText
import com.vr.stringgeneratorcp.model.RandomTextData
import com.vr.stringgeneratorcp.repository.RoomRepository
import com.vr.stringgeneratorcp.repository.StringGeneratorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    val context: Application,
    private val myStringRepository: StringGeneratorRepository,
    private val dbRepository: RoomRepository
) : AndroidViewModel(context) {

    var generatedString: MutableLiveData<RandomText> = MutableLiveData()
    fun generateString(limit: Int): LiveData<RandomText> {
        viewModelScope.launch {
            generatedString.value = myStringRepository.generatorString(context, limit)
        }
        return generatedString
    }

    fun getGeneratedString() =
        dbRepository.getAllStringData().asLiveData(viewModelScope.coroutineContext)

    fun update(data: RandomTextData) {
        viewModelScope.launch {
            dbRepository.update(data)
        }
    }

    fun delete(data: RandomTextData) {
        viewModelScope.launch {
            dbRepository.delete(data)
        }
    }

    fun deletAll(){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.deleteAll()
        }
    }
}