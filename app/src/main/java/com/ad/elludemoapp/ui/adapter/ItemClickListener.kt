package com.ad.elludemoapp.ui.adapter

import com.ad.elludemoapp.model.Item
import com.ad.elludemoapp.model.Specification

interface ItemClickListener {
    fun onRadioChecked(item: Specification, childItem: Item, position: Int)
    fun onChildItemQtyChange(item: Specification, childItem: Item, position: Int)
}