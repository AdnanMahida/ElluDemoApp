package com.ad.elludemoapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ad.elludemoapp.databinding.ActivityMainBinding
import com.ad.elludemoapp.model.ProductItems
import com.ad.elludemoapp.util.getJsonDataFromAsset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var productItems: ProductItems? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProductListFromJson()
        initUi()
    }

    private fun initUi() {
        binding.apply {
            tvPacketTitle.text = productItems?.name?.firstOrNull()
            tvPacketPrice.text =
                "₹ ${productItems?.specifications?.firstOrNull { it.isParentAssociate }?.list?.firstOrNull { it.isDefaultSelected }?.price ?: 0.00}"
            btnCustomize.setOnClickListener {
                if (productItems != null) {
                    startActivity(CartActivity.getIntent(this@MainActivity, productItems!!))
                }
            }
        }
    }

    private fun getProductListFromJson() {
        val jsonFileString = getJsonDataFromAsset(fileName = "items.json")
        val listProductType = object : TypeToken<ProductItems>() {}.type
        productItems = Gson().fromJson(jsonFileString, listProductType)
        Log.e("Product", "Items :: ${productItems?.specifications.toString()}")
    }

}