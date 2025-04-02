package com.example.mdp.ui.components.profilepage.bmicomponent.logic

fun calculateBMI(weight: Double, height: Double): Double {
    return if (height > 0.0 || weight > 0.0) {
        weight / (height * height)
    } else {
        0.0
    }
}