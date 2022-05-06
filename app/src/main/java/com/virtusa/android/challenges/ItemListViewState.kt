package com.virtusa.android.challenges


data class ItemListViewState(
    val items: List<ItemRow>
)

data class ItemRow(val name: String)