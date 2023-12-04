package icesi.edu.co.mercatero.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.mercatero.databinding.FragmentShopDescriptionBinding
import icesi.edu.co.mercatero.model.Product
import icesi.edu.co.mercatero.view.adapters.home.ProductAdapter
import icesi.edu.co.mercatero.viewmodel.home.ShopDescriptionViewModel
import java.util.ArrayList

class ShopDescriptionFragment(private val product_id: String) : Fragment() {

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
        shopViewModel.getStoreInfo(product_id)
        shopViewModel.store.observe(viewLifecycleOwner){

            binding.shopName.text = it.name
             val text = "Tienda " + it.name  + ", Ubicado en " + it.address + " .\nContacto para Domicilios: " + it.phone + " ."
             binding.shopDescription.text = text

        }
        shopViewModel.products.observe(viewLifecycleOwner){

            binding.productsRecyclerView.adapter = ProductAdapter(requireContext(), it)
            binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        }


       // initRecycleView()
    }
    
    companion object {
        @JvmStatic
        fun newInstance(product_id: String) = ShopDescriptionFragment(product_id)
    }
}