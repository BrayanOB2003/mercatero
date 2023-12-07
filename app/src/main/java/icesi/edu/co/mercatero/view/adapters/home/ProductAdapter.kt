package icesi.edu.co.mercatero.view.adapters.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.model.Product

class ProductAdapter(
    private val context: Context,
    private val data: ArrayList<Product>,
    private val listener: OnProductItemClickListener
    ): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {


   /* fun addProduct(product: Product) {
        data.add(product)
        notifyItemInserted(itemCount)
    }
    */

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.productLogo)
        var productName: TextView = itemView.findViewById(R.id.productName)
        var shopName: TextView = itemView.findViewById(R.id.shopName)
        var price: TextView = itemView.findViewById(R.id.productPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_product_button, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = data[position]

        if (!product.imageURL.isNullOrBlank()) {
            val storageReference: StorageReference = FirebaseStorage.getInstance()
                .getReferenceFromUrl(product.imageURL)

            storageReference.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(context)
                    .load(uri)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.image)
            }
        } else {
            Glide.with(context)
                .load(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.image)
        }


        holder.productName.text = product.name
        holder.shopName.text = product.shopName
        holder.price.text = product.price

        holder.itemView.setOnClickListener{
            listener.onProductItemClick(product)
        }
    }



}