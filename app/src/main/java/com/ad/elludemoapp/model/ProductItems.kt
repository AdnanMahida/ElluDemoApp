package com.ad.elludemoapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductItems(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: List<String>,
    @SerializedName("price")
    val price: Int,
    @SerializedName("item_taxes")
    val itemTaxes: List<Int>,
    @SerializedName("specifications")
    val specifications: List<Specification>
) : Parcelable