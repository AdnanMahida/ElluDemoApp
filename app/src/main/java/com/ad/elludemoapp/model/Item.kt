package com.ad.elludemoapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("_id")
    val id: String,
    @SerializedName("is_default_selected")
    var isDefaultSelected: Boolean,
    @SerializedName("name")
    val name: List<String>,
    @SerializedName("price")
    val price: Double,
    @SerializedName("sequence_number")
    val sequenceNumber: Int,
    @SerializedName("specification_group_id")
    val specificationGroupId: String,
    @SerializedName("unique_id")
    val uniqueId: Int,

    var qty: Int = 0
) : Parcelable {
    fun getPrice(): String {
        return if (price > 0) "₹ $price" else ""
    }
}