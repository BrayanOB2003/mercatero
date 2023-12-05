package icesi.edu.co.mercatero.view.adapters.shopHome

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.model.Order
import icesi.edu.co.mercatero.view.adapters.home.ProductAdapter

class OrderAdapter(
    private val data: ArrayList<Order>,
    private val clientNames: ArrayList<String>,
    private val listener: OnClickOrderButton
): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.orderName)
        var count: TextView = itemView.findViewById(R.id.itemsCount)
        var total: TextView = itemView.findViewById(R.id.totalPrice)
        var button: Button = itemView.findViewById(R.id.orderButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_order, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var order = data[position]
        var name = clientNames[position]

        holder.name.text = holder.name.text.toString() + name
        holder.count.text = holder.count.text.toString() + order.quantities.size
        holder.total.text = holder.total.text.toString() + order.price

        holder.button.setOnClickListener {
            order.order_id?.let { it1 -> listener.onClickUpdateStatus(it1) }
        }
    }
}