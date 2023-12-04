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


class ShopAdapter(
    private val context: Context,
    private val data: Array<Pair<String, String>>): RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.shopLogo)
        var name: TextView = itemView.findViewById(R.id.shopName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_shop_button, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (imageUrl, storeName) = data[position]
        val storageReference: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl)

        storageReference.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(context)
                .load(uri)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.image)
        }

        holder.name.text = storeName
    }

    override fun getItemCount(): Int {
        return data.size
    }
}