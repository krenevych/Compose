package com.example.broadcastflow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class TrackerViewModel(context: Application) :
    AndroidViewModel(context) {

    private val broadcastTracker = BroadcastTracker(context)

    val tracker = broadcastTracker.tracker.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = "Undefined action"
    )

}