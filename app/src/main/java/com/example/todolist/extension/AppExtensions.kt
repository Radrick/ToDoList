package com.example.todolist.extension

import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale("pt", "br")

fun Date.format() : String{
    return SimpleDateFormat("dd/MM/yyyy", locale).format(this)
}