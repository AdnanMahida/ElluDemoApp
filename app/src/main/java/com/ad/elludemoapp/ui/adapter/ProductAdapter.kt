package com.ad.elludemoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ad.elludemoapp.databinding.ItemProductBinding
import com.ad.elludemoapp.model.Specification

class ProductAdapter :
    RecyclerView.Adapter<ProductAdapter.SpecificationGroupViewHolder>() {
    val productList = mutableListOf<Specification>()
    private var mClickListener: ItemClickListener? = null


    fun addAllProducts(formList: MutableList<Specification>) {
        productList.addAll(formList)
        notifyDataSetChanged()
    }

    inner class SpecificationGroupViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(specificationGroup: Specification) {
            binding.tvProductDetailsTitle.text = specificationGroup.name.firstOrNull().toString()
            binding.tvProductDetailsSubTitle.text = specificationGroup.getRangeDetails()
            setUpChildRv(binding, specificationGroup)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpecificationGroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return SpecificationGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecificationGroupViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size


    private fun setUpChildRv(binding: ItemProductBinding, spec: Specification) {
        val adapter = ProductChildAdapter(
            spec = spec,
            spec.userCanAddSpecificationQuantity,
        )

        adapter.setClickListener(mClickListener ?: return)
        binding.rvChild.adapter = adapter
        adapter.addAllProducts(spec.list.toMutableList())
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        mClickListener = itemClickListener
    }

}