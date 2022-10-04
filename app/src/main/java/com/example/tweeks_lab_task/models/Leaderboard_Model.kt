package com.example.tweeks_lab_task.models

data class Athlete (
    val id: String,
    val name: String,
    val score: Int,
    val runup: Int,
    val jump: Int,
    val bfc: Int,
    val ffc: Int,
    var selected:Boolean?=null,
    val release: Int
)