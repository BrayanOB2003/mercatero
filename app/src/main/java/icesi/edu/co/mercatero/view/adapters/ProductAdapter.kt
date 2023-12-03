package icesi.edu.co.mercatero.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.ProductManageBinding
import icesi.edu.co.mercatero.model.shop.Product

class ProductAdapter(private var products: Array<Product>) : Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val binding = ProductManageBinding.bind(root)
        val imageProduct = binding.imageProduct
        val nameProduct = binding.textViewProductName
        val priceProduct = binding.textViewProductPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_manage, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameProduct.text = products[position].name
        holder.priceProduct.text = products[position].price.toString()
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setProducts(newProducts: Array<Product>) {
        products = newProducts
    }
}