package com.aas.footballapp.ui.table

import com.aas.footballapp.data.model.Table

interface TableInterface {
    fun showLoading()
    fun hideLoading()
    fun tableData(table: List<Table>)
}