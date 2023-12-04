package icesi.edu.co.mercatero.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentProductDescriptionBinding
import icesi.edu.co.mercatero.model.Product
import icesi.edu.co.mercatero.view.adapters.home.ProductAdapter
import icesi.edu.co.mercatero.view.adapters.home.ShopAdapter
import java.util.ArrayList

class ProductDescriptionFragment(private val product_id: String) : Fragment() {

    private lateinit var binding: FragmentProductDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDescriptionBinding.inflate(inflater,container,  false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycleView()
    }

    private fun initRecycleView(){

        val products = ArrayList<Product>()
         var product =  Product(
                "1",
                "Producto 1",
                "Descripción del Producto 1",
                19.99,
                "gs://mercatero.appspot.com/product/product_test.png",
                "Tienda A"
            )
         var product2 =  Product(
                "2",
                "Producto 2",
                "Descripción del Producto 2",
                29.99,
                "gs://mercatero.appspot.com/product/product_test2.png",
                "Tienda B"
            )
         var product3 =  Product(
                "3",
                "Producto 3",
                "Descripción del Producto 3", 39.99,
                "gs://mercatero.appspot.com/product/product_test.png",
                "Tienda C"
            )
        products.add(product)
        products.add(product2)
        products.add(product3)

        binding.productsRecyclerView.adapter = ProductAdapter(requireContext(), products)
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(product_id: String) = ProductDescriptionFragment(product_id)
    }
}