package com.ad.elludemoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ad.elludemoapp.databinding.ItemProductChildBinding
import com.ad.elludemoapp.model.Item
import com.ad.elludemoapp.model.Specification
import com.ad.elludemoapp.util.Constant

class ProductChildAdapter(
    private val spec: Specification,
    private val userCanAddSpecificationQuantity: Boolean
) :
    RecyclerView.Adapter<ProductChildAdapter.SpecificationGroupViewHolder>() {

    var mClickListener: ItemClickListener? = null
    private val productList = mutableListOf<Item>()

    fun addAllProducts(formList: MutableList<Item>) {
        productList.addAll(formList)
        notifyDataSetChanged()
    }

    inner class SpecificationGroupViewHolder(private val binding: ItemProductChildBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(specificationGroup: Item) {
            binding.apply {
                tvTitle.text = specificationGroup.name.firstOrNull().toString()
                tvAmount.text = specificationGroup.getPrice()
                radio.isInvisible = spec.type == Constant.TYPE_MULTI_SELECTION
                checkBox.isInvisible = spec.type == Constant.TYPE_SINGLE_SELECTION

                checkBox.isChecked = specificationGroup.isDefaultSelected
                radio.isChecked = specificationGroup.isDefaultSelected


                qtyView.isVisible =
                    (userCanAddSpecificationQuantity && checkBox.isChecked)


                radio.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        mClickListener?.onRadioChecked(
                            position = adapterPosition,
                            item = spec,
                            childItem = specificationGroup
                        )
                    }
                }
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    qtyView.isVisible = (isChecked && userCanAddSpecificationQuantity)

                    if (userCanAddSpecificationQuantity) {
                        if (isChecked) {
                            specificationGroup.qty = 1
                            qtyView.setQuantity(specificationGroup.qty)
                        } else {
                            specificationGroup.qty = 0
                        }
                    }
                    mClickListener?.onChildItemQtyChange(
                        position = adapterPosition,
                        item = spec,
                        childItem = specificationGroup
                    )
                }
                qtyView.setOnQtyChangeListener { qty ->
                    if (qty <= 0) {
                        qtyView.isVisible = false
                        checkBox.isChecked = false
                    }
                    specificationGroup.qty = qty
                    mClickListener?.onChildItemQtyChange(
                        position = adapterPosition,
                        item = spec,
                        childItem = specificationGroup
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpecificationGroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductChildBinding.inflate(inflater, parent, false)
        return SpecificationGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecificationGroupViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    fun setClickListener(itemClickListener: ItemClickListener) {
        mClickListener = itemClickListener
    }

}