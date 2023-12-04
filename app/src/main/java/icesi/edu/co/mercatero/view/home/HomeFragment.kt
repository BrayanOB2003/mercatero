package icesi.edu.co.mercatero.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.mercatero.databinding.FragmentHomeBinding
import icesi.edu.co.mercatero.view.adapters.home.ButtonAdapter
import icesi.edu.co.mercatero.view.adapters.home.OnShopItemClickListener
import icesi.edu.co.mercatero.view.adapters.home.ProductAdapter
import icesi.edu.co.mercatero.view.adapters.home.ShopAdapter
import icesi.edu.co.mercatero.viewmodel.home.HomeViewModel

class HomeFragment : Fragment(),OnShopItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = HomeViewModel()
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

        val data = arrayOf(
            Pair("gs://mercatero.appspot.com/shop/shop_test.png", "Tiendita la esquinita"),
            Pair("gs://mercatero.appspot.com/shop/shop_test2.png", "Tienda los semanales"),
            Pair("gs://mercatero.appspot.com/shop/shop_test.png", "Tiendita la esquinita"),
            Pair("gs://mercatero.appspot.com/shop/shop_test2.png", "Tienda los semanales"),
            Pair("gs://mercatero.appspot.com/shop/shop_test.png", "Tiendita la esquinita"),
            Pair("gs://mercatero.appspot.com/shop/shop_test2.png", "Tienda los semanales")
        )
        viewModel.getProductList()
        viewModel.getStoreList()
        viewModel.products.observe(viewLifecycleOwner){

           // binding.productsRecyclerView.adapter
            Log.d("Test", "Esto llega al observe " + it.toString())
            if(it.isNotEmpty()) {
                binding.productsRecyclerView.adapter = ProductAdapter(requireContext(), it)
            }
        }

        viewModel.stores.observe(viewLifecycleOwner){

            Log.d("Test", "Esto esta en el observe " + it.size.toString())
          //  Log.d("Test", "En el observe de store hay " + it.get(0).toString())
          //  Log.d("Test", "En el observe de store hay " + it.get(1).toString())

            if (it.isNotEmpty()) {
                binding.shopsRecyclerView.adapter = ShopAdapter(requireContext(), it, this)
                binding.shopsRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
/*
        val products = ArrayList<Product>()
          val  product =  Product(
                "1",
                "Producto 1",
                "Descripción del Producto 1",
                19.99,
                "gs://mercatero.appspot.com/product/product_test.png",
                "Tienda A"
            )

        val product2 =  Product(
                "3",
                "Producto 3",
                "Descripción del Producto 3",
                39.99,
                "gs://mercatero.appspot.com/product/product_test.png",
                "Tienda C"
            )

 */
        //products.add(product)
        //products.add(product2)


       // binding.productsRecyclerView.adapter = ProductAdapter(requireContext(), products)
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onShopItemClick(shop_id: String) {
        val homeActivity = activity as HomeActivity
        homeActivity.loadFragment(ShopDescriptionFragment.newInstance(shop_id))
    }
}