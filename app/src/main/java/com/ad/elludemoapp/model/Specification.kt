package com.ad.elludemoapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Specification(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: List<String>,
    @SerializedName("unique_id")
    val uniqueId: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("range")
    val range: Int,
    @SerializedName("max_range")
    val maxRange: Int,
    @SerializedName("is_required")
    val isRequired: Boolean,
    @SerializedName("isParentAssociate")
    val isParentAssociate: Boolean,
    @SerializedName("isAssociated")
    val isAssociated: Boolean,
    @SerializedName("modifierGroupId")
    val modifierGroupId: String? = null,
    @SerializedName("modifierGroupName")
    val modifierGroupName: String? = null,
    @SerializedName("modifierName")
    val modifierName: String? = null,
    @SerializedName("modifierId")
    val modifierId: String? = null,
    @SerializedName("sequence_number")
    val sequenceNumber: Int,
    @SerializedName("user_can_add_specification_quantity")
    val userCanAddSpecificationQuantity: Boolean,
    @SerializedName("list")
    val list: MutableList<Item>,
) : Parcelable {

    fun getDefaultSelectedPrice(): Double {
        return list.firstOrNull { it.isDefaultSelected }?.price ?: 0.0
    }

    fun getRangeDetails(): String {
        return when (type) {
            1 -> if (range == 1) "Choose $range" else ""
            2 -> "Choose up to $maxRange"
            else -> ""
        }
    }
}