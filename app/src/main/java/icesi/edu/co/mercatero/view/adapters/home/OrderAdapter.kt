package icesi.edu.co.mercatero.view.adapters.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.model.Order
import icesi.edu.co.mercatero.model.enumeration.OrderStatus

class OrderAdapter(private val data: ArrayList<Order>): RecyclerView.Adapter<OrderAdapter.ViewHolder>()  {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.orderName)
        var count: TextView = itemView.findViewById(R.id.itemsCount)
        var total: TextView = itemView.findViewById(R.id.totalPrice)
        var status: TextView = itemView.findViewById(R.id.orderStatus)
        var button: Button = itemView.findViewById(R.id.orderButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_order, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var order = data[position]
        holder.name.text = holder.name.text.toString() + position
        holder.count.text = holder.count.text.toString() + order.idProducts.size
        holder.total.text = holder.total.text.toString() + order.price
        holder.status.text = defineStatus(order.status)
        holder.button.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun defineStatus(status: String): String {
        if (status == OrderStatus.TO_DO.toString()) {
            return "Pedido iniciado"
        }else if (status == OrderStatus.IN_PROGRESS.toString()) {
            return "Pedido en proceso"
        }else if (status == OrderStatus.TO_DELIVER.toString()) {
            return "Envio enviado"
        }else if (status == OrderStatus.DELIVERED.toString()) {
            return "Pedido entregado"
        }
        return ""
    }
}