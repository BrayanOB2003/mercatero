package icesi.edu.co.mercatero.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import icesi.edu.co.mercatero.R

class ButtonAdapter(private val context: Context, private val buttons: Array<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return buttons.size
    }

    override fun getItem(position: Int): Any {
        return buttons[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val button: Button
        if (convertView == null) {
            // Si la vista no existe, inflarla desde el layout
            val inflater = LayoutInflater.from(context)
            button = inflater.inflate(R.layout.list_item_button, parent, false) as Button
        } else {
            // Si la vista existe, reutilizarla
            button = convertView as Button
        }
        button.text = buttons[position]

        return button
    }
}