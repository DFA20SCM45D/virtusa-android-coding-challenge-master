package com.virtusa.android.challenges

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    interface UpdateListener {
        fun onUpdate(state: ItemListViewState)
    }

    private var itemListViewState: ItemListViewState
    private var listener: UpdateListener? = null

    init {
        val items = listOf(
            ItemRow("Cabbage"),
            ItemRow("Apple"),
            ItemRow("Bread")
        )
        itemListViewState = ItemListViewState(items)
    }

    fun setStateUpdateListener(listener: UpdateListener?) {
        this.listener = listener
    }
}
