package icesi.edu.co.mercatero.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.ProductManageBinding
import icesi.edu.co.mercatero.model.Product

class ProductAdapter(
    private val context: Context,
    private var products: Array<Product>
) : Adapter<ProductAdapter.ViewHolder>() {

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
        val product = products[position]
        if (!product.imageURL.isNullOrBlank()) {
            val storageReference: StorageReference = FirebaseStorage.getInstance()
                .getReferenceFromUrl(product.imageURL)

            storageReference.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(context)
                    .load(uri)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.imageProduct)
            }
        } else {
            Glide.with(context)
                .load(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imageProduct)
        }
        holder.nameProduct.text = product.name
        holder.priceProduct.text = product.price
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setProducts(newProducts: Array<Product>) {
        products = newProducts
    }
}