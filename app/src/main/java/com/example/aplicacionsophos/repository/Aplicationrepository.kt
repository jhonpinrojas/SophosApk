package com.example.aplicacionsophos.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.aplicacionsophos.data.model.Office
import com.example.aplicacionsophos.service.Api

class Aplicationrepository(private val Api:Api) {
    //private var officeList: List<Office> = emptyList<Office>()
    private val oficeListLiveData = MutableLiveData<List<Office>>()
    val oficeList: LiveData<List<Office>>
        get() = oficeListLiveData
   /* suspend fun getOficeList(){
        val result=Api.getofices()
        if()
    }*/
}