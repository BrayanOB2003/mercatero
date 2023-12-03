package icesi.edu.co.mercatero.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.mercatero.databinding.FragmentHomeBinding
import icesi.edu.co.mercatero.model.Product
import icesi.edu.co.mercatero.view.adapters.home.ButtonAdapter
import icesi.edu.co.mercatero.view.adapters.home.ProductAdapter
import icesi.edu.co.mercatero.view.adapters.home.ShopAdapter
import icesi.edu.co.mercatero.viewmodel.home.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var productsAdapater: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = HomeViewModel()
        productsAdapater = ProductAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycleView()
    }

    private fun initRecycleView(){

        val buttons = arrayOf("Botón 1", "Botón 2", "Botón 3", "Botón 4", "Botón 5","Botón 1", "Botón 2", "Botón 3", "Botón 4", "Botón 5")

        binding.categoriesRecyclerView.adapter = ButtonAdapter(buttons)
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.getProductList()
        val data = arrayOf(
            Pair("gs://mercatero.appspot.com/shop/shop_test.png", "Tiendita la esquinita"),
            Pair("gs://mercatero.appspot.com/shop/shop_test2.png", "Tienda los semanales"),
            Pair("gs://mercatero.appspot.com/shop/shop_test.png", "Tiendita la esquinita"),
            Pair("gs://mercatero.appspot.com/shop/shop_test2.png", "Tienda los semanales"),
            Pair("gs://mercatero.appspot.com/shop/shop_test.png", "Tiendita la esquinita"),
            Pair("gs://mercatero.appspot.com/shop/shop_test2.png", "Tienda los semanales")
        )

        viewModel.products.observe(viewLifecycleOwner){

            productsAdapater.addProduct(it)


        }
        binding.shopsRecyclerView.adapter = ShopAdapter(requireContext(), data)
        binding.shopsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        /*
        val products = arrayOf(
            Product(
                "1",
                "Producto 1",
                "Descripción del Producto 1",
                "$19.99",
                "gs://mercatero.appspot.com/product/product_test.png",
                "Tienda A"
            ),
            Product(
                "2",
                "Producto 2",
                "Descripción del Producto 2",
                "$29.99",
                "gs://mercatero.appspot.com/product/product_test2.png",
                "Tienda B"
            ),
            Product(
                "3",
                "Producto 3",
                "Descripción del Producto 3",
                "$39.99",
                "gs://mercatero.appspot.com/product/product_test.png",
                "Tienda C"
            )
        )
        */

       // binding.productsRecyclerView.adapter = ProductAdapter(requireContext(), products)
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}