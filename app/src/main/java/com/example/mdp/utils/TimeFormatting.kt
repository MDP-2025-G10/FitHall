package com.example.mdp.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun historyCardTimeFormatter(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy-MMM-dd HH:mm", Locale.getDefault()) // Format: YYYY-MM-DD HH:MM
    return sdf.format(Date(timestamp))
}

fun lineChartTimeFormatter(dateStr: String?): String {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("MMM-d")
        LocalDate.parse(dateStr, inputFormatter).format(outputFormatter)
    } catch (e: Exception) {
        dateStr ?: ""
    }
}

fun dateSelectorTimeFormatter(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd")
    return date.format(formatter)
}

fun profileTimeFormatter(date:LocalDate):String{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd")
    return date.format(formatter)
}