package com.genius.wasylews.myteviant.common.ui

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun NavController.tryNavigate(directions: NavDirections) {
    runCatching { navigate(directions.actionId, directions.arguments) }
        .onFailure { it.printStackTrace() }
}

fun Fragment.tryNavigate(directions: NavDirections) {
    findNavController().tryNavigate(directions)
}