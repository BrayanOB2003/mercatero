package icesi.edu.co.mercatero.view.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.mercatero.databinding.FragmentHomeBinding
import icesi.edu.co.mercatero.model.Product
import icesi.edu.co.mercatero.model.Shop
import icesi.edu.co.mercatero.view.adapters.home.ButtonAdapter
import icesi.edu.co.mercatero.view.adapters.home.OnProductItemClickListener
import icesi.edu.co.mercatero.view.adapters.home.OnShopItemClickListener
import icesi.edu.co.mercatero.view.adapters.home.ProductAdapter
import icesi.edu.co.mercatero.view.adapters.home.ShopAdapter
import icesi.edu.co.mercatero.view.home.HomeActivity
import icesi.edu.co.mercatero.viewmodel.home.HomeViewModel

class HomeFragment : Fragment(),OnShopItemClickListener, OnProductItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
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

        if(!viewModel.productsLoaded){
            viewModel.getProductList()
            viewModel.products.observe(viewLifecycleOwner){
                if(it.isNotEmpty()) {
                    binding.productsRecyclerView.adapter = ProductAdapter(requireContext(), it, this)
                    binding.productsRecyclerView.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
            }
        } else {
            binding.productsRecyclerView.adapter =
                viewModel.products?.value?.let { ProductAdapter(requireContext(), it, this) }
            binding.productsRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        if(!viewModel.storesLoaded){
            viewModel.getStoreList()
            viewModel.stores.observe(viewLifecycleOwner){
                if (it.isNotEmpty()) {
                    binding.shopsRecyclerView.adapter = ShopAdapter(requireContext(), it, this)
                    binding.shopsRecyclerView.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                }
            }
        } else {
            binding.shopsRecyclerView.adapter =
                viewModel.stores.value?.let { ShopAdapter(requireContext(), it, this) }
            binding.shopsRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
    override fun onProductItemClick(product: Product) {
        val homeActivity = activity as HomeActivity
        homeActivity.loadFragment(ProductViewFragment(product), BackStack.HOME.name)
    }

    override fun onShopItemClick(shop: Shop) {
        val homeActivity = activity as HomeActivity
        homeActivity.loadFragment(ShopDescriptionFragment.newInstance(shop), BackStack.HOME.name)
    }
}