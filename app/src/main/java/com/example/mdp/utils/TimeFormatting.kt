package com.example.mdp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTimestampToDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()) // Format: YYYY-MM-DD HH:MM
    return sdf.format(Date(timestamp))
}