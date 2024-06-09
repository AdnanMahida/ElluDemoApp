package com.ad.elludemoapp.util.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ad.elludemoapp.databinding.QuantitySelectorViewBinding

class QuantitySelectorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: QuantitySelectorViewBinding =
        QuantitySelectorViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var qtyListener: QtyChangeListener? = null
    private var quantity: Int = 0

    init {
        binding.btnAdd.setOnClickListener {
            increaseQuantity()
        }

        binding.btnMinus.setOnClickListener {
            decreaseQuantity()
        }
    }

    fun getQuantity(): Int {
        return quantity
    }

    fun setQuantity(quantity: Int) {
        this.quantity = quantity
        updateQuantityText()
    }

    private fun increaseQuantity() {
        quantity++
        updateQuantityText()
        qtyListener?.onQtyChange(quantity)
    }

    private fun decreaseQuantity() {
        if (quantity > 0) {
            quantity--
            updateQuantityText()
            qtyListener?.onQtyChange(quantity)
        }
    }

    private fun updateQuantityText() {
        binding.tvQuantity.text = quantity.toString()
    }

    fun interface QtyChangeListener {
        fun onQtyChange(qty: Int)
    }

    fun setOnQtyChangeListener(qty: QtyChangeListener) {
        qtyListener = qty
    }
}