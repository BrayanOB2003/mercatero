package icesi.edu.co.mercatero.view.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.mercatero.databinding.FragmentShopDescriptionBinding
import icesi.edu.co.mercatero.model.Product
import icesi.edu.co.mercatero.model.Shop
import icesi.edu.co.mercatero.view.adapters.home.OnProductItemClickListener
import icesi.edu.co.mercatero.view.adapters.home.ProductAdapter
import icesi.edu.co.mercatero.view.home.HomeActivity
import icesi.edu.co.mercatero.viewmodel.home.ShopDescriptionViewModel

class ShopDescriptionFragment(private val shop: Shop) : Fragment(), OnProductItemClickListener {

    private lateinit var binding: FragmentShopDescriptionBinding
    private lateinit var shopViewModel: ShopDescriptionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopDescriptionBinding.inflate(inflater,container,  false)
        shopViewModel = ShopDescriptionViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener{
            val homeActivity = activity as HomeActivity
            homeActivity.loadFragment(homeActivity.homeFragment)
        }

        binding.shopName.text = shop.name
        val text = "Tienda " + shop.name  + ", Ubicado en " + shop.address + " .\nContacto para Domicilios: " + shop.phone + " ."
        binding.shopDescription.text = text

        shop.shop_id?.let { shopViewModel.getStoreInfo(it) }

        shopViewModel.products.observe(viewLifecycleOwner){
            binding.productsRecyclerView.adapter = ProductAdapter(requireContext(), it, this)
            binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(shop: Shop) = ShopDescriptionFragment(shop)
    }

    override fun onProductItemClick(product: Product) {
        val homeActivity = activity as HomeActivity
        homeActivity.loadFragment(ProductViewFragment(product))
    }
}