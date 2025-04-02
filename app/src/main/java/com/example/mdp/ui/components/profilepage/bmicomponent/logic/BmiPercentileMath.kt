package com.example.mdp.ui.components.profilepage.bmicomponent.logic

import kotlin.math.exp

fun estimateBMIPercentile(bmi: Double, age: Int, sex: String): Double {
    val (a, b) = getRegressionCoefficients(age, sex)
    val percentile = 1 / (1 + exp(-(a + b * bmi)))
    return percentile * 100 // Convert to percentage (0-100)
}

fun getRegressionCoefficients(age: Int, sex: String): Pair<Double, Double> {
    return when {
        age < 5 -> if (sex == "male") Pair(-4.5, 0.18) else Pair(-4.3, 0.17)
        age < 10 -> if (sex == "male") Pair(-4.2, 0.19) else Pair(-4.0, 0.18)
        age < 15 -> if (sex == "male") Pair(-4.0, 0.2) else Pair(-3.8, 0.19)
        else -> if (sex == "male") Pair(-3.8, 0.22) else Pair(-3.6, 0.21)
    }
}