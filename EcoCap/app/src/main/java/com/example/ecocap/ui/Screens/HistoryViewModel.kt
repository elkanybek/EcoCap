package com.example.ecocap.ui.Screens

import androidx.lifecycle.ViewModel

class HistoryViewModel: ViewModel() {
    var sessionId: Int = 1

    val animals = listOf(
        "Frog",
        "Tiger",
        "Elephant",
        "Penguin",
        "Panda",
        "Koala",
        "Giraffe",
        "Lion",
        "Zebra",
        "Kangaroo",
        "Polar Bear"
    )
}