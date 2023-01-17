package com.app.acrominetest.viewmodels

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.acrominetest.models.AcromineResponse
import com.app.acrominetest.repository.AcromineRepository
import com.app.acrominetest.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AcromineViewModel @Inject constructor(private val repository: AcromineRepository) :
    ViewModel() {


    private val _acromine =
        MutableLiveData<NetworkResult<List<AcromineResponse>>>(NetworkResult.Loading())
    val acromineLiveData: LiveData<NetworkResult<List<AcromineResponse>>> = _acromine

    //variable that will listen response and based on it hide/show layout in XML
    var inputAbbreviation = MutableLiveData(false)
    var abbreviationErrorMessage = MutableLiveData("No data found. Search for Abbreviations!!")

    fun onTextChange(editable: Editable?) {
        if (editable.toString().isNotEmpty())
            fetchAbbreviation(editable.toString())
    }

    // Executes when searching from edittext
    fun fetchAbbreviation(sf: String) {
        viewModelScope.launch {
            val result = repository.fetchAcromine(sf)
            _acromine.postValue(result)
        }
    }
}
