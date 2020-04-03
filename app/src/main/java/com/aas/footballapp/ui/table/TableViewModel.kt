package com.aas.footballapp.ui.table

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aas.footballapp.data.model.Table

class TableViewModel: ViewModel(){
    private var listTable = MutableLiveData<List<Table>>()

    fun setTable(table: List<Table>){
        listTable.postValue(table)
    }

    fun getTable() : LiveData<List<Table>>{
        return listTable
    }
}