package com.example.mdp.ui.components.profile.bmicomponent.logic

fun categorizeBMI(bmi: Double, age: Int, sex: String): String {
    return if (age <= 20) {
        val percentile = estimateBMIPercentile(bmi, age, sex)
        when {
            percentile < 5 -> "Underweight"
            percentile < 85 -> "Normal weight"
            percentile < 95 -> "Overweight"
            else -> "Obese"
        }
    } else {
        when {
            bmi < 16 -> "Severely underweight"
            bmi < 16.9 -> "Underweight"
            bmi < 18.4 -> "Mildly underweight"
            bmi < 24.9 -> "Normal weight"
            bmi < 29.9 -> "Overweight"
            bmi < 34.9 -> "Obesity class I (Moderate)"
            bmi < 39.9 -> "Obesity class II (Severe)"
            else -> "Obesity class III (Very severe or morbid)"
        }
    }
}

