package com.ad.elludemoapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ad.elludemoapp.R
import com.ad.elludemoapp.databinding.ActivityCartBinding
import com.ad.elludemoapp.model.Item
import com.ad.elludemoapp.model.ProductItems
import com.ad.elludemoapp.model.Specification
import com.ad.elludemoapp.ui.adapter.ItemClickListener
import com.ad.elludemoapp.ui.adapter.ProductAdapter
import com.ad.elludemoapp.util.parcelable

class CartActivity : AppCompatActivity() {

    companion object {
        private const val KEY_PRODUCT = "product"
        fun getIntent(context: Context, productItems: ProductItems): Intent {
            return Intent(context, CartActivity::class.java).apply {
                putExtra(KEY_PRODUCT, productItems)
            }
        }
    }

    private val product by lazy {
        intent.parcelable<ProductItems>(KEY_PRODUCT) as ProductItems
    }

    private lateinit var binding: ActivityCartBinding
    private val adapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        binding.toolbar.setNavigationOnClickListener { finish() }

        val parentItem = product.specifications.firstOrNull { it.isParentAssociate }
        val arrayList = getFilterData(parentItem)
        if (parentItem != null) {
            arrayList[0] = parentItem
        }

        adapter.setClickListener(object : ItemClickListener {
            override fun onRadioChecked(item: Specification, childItem: Item, position: Int) {
                val childIndex = item.list.indexOf(childItem)
                adapter.productList.forEach { it.list.forEach { it.isDefaultSelected = false } }
                childItem.isDefaultSelected = true
                adapter.notifyItemChanged(position)
                adapter.productList.clear()
                item.list[childIndex] = childItem
                adapter.productList.add(item)
                adapter.productList.addAll(getFilterData(item))
                adapter.notifyDataSetChanged()
                setUpPrice(item.getDefaultSelectedPrice())
            }

            override fun onChildItemQtyChange(item: Specification, childItem: Item, position: Int) {
                setUpPrice(getCalculatedPrice())
            }
        })
        binding.rvList.adapter = adapter
        adapter.addAllProducts(arrayList.toMutableList())

        setUpPrice(parentItem?.getDefaultSelectedPrice() ?: 0.0)
    }

    private fun getFilterData(parentItem: Specification?): MutableList<Specification> {
        val returnList = mutableListOf<Specification>()
        parentItem?.list?.forEach { child ->
            product.specifications.forEach { childSpec ->
                if (child.id == childSpec.modifierId && child.isDefaultSelected) {
                    returnList.add(childSpec)
                }
            }
        }
        return returnList
    }

    private fun getCalculatedPrice(): Double {
        var totalPrice =
            adapter.productList.firstOrNull { it.isParentAssociate }?.list?.firstOrNull { it.isDefaultSelected }?.price
                ?: 0.0
        adapter.productList.filter { it.userCanAddSpecificationQuantity }.forEach {
            it.list.forEach {
                totalPrice += (it.price * it.qty)
            }
        }
        return totalPrice
    }

    private fun setUpPrice(price: Double) {
        binding.btnAddToCart.setText(getString(R.string.add_to_cart, price.toString()))
    }
}