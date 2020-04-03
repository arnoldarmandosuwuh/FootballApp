package com.aas.footballapp.data.model

data class Table(
    val teamid: String,
    val name: String,
    val played: String,
    val goalsfor: String,
    val goalsagainst: String,
    val goalsdifference: String,
    val win: String,
    val draw: String,
    val loss: String,
    val total: String
)