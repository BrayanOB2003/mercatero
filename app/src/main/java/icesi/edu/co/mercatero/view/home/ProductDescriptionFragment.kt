package icesi.edu.co.mercatero.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import icesi.edu.co.mercatero.R

class ProductDescriptionFragment(private val product_id: String) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_description, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(product_id: String) = ProductDescriptionFragment(product_id)
    }
}