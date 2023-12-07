package icesi.edu.co.mercatero.view.shopProducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentManageViewProductsBinding
import icesi.edu.co.mercatero.model.Product
import icesi.edu.co.mercatero.view.adapters.home.OnProductItemClickListener
import icesi.edu.co.mercatero.view.adapters.home.ProductAdapter
import icesi.edu.co.mercatero.viewmodel.product.ProductViewModel

class ViewProductsFragment() : Fragment(), OnProductItemClickListener {

    private lateinit var binding: FragmentManageViewProductsBinding
    private val productViewModel: ProductViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageViewProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.getAuthUser()
        productViewModel.shopAuth.observe(viewLifecycleOwner){
            val authShop = productViewModel.shopAuth.value
            binding.welcomeText.text = binding.welcomeText.text.toString() + authShop?.name
        }


        binding.buttonBack.setOnClickListener{
            activity?.finish()
        }
        productViewModel.loadMyProducts()
        productViewModel.myProducts.observe(viewLifecycleOwner){

            if(it.isEmpty()){
                binding.warningEmpty.text = getText(R.string.add_product_empty)
                binding.buttonAddProduct.isVisible = true
                binding.addFloatingButton.isVisible = false
            }else{
                binding.warningEmpty.text = getText(R.string.add_product_fill)
                binding.buttonAddProduct.isVisible = false
                binding.addFloatingButton.isVisible = true

                binding.productList.adapter = ProductAdapter(requireContext(), it!!, this)
                binding.productList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            }


        }

        binding.buttonAddProduct.setOnClickListener {
            val manageProductActivity = activity as ManageProductsActivity
            manageProductActivity.loadFragment(manageProductActivity.addProductFragment)
        }

        binding.addFloatingButton.setOnClickListener{
            val manageProductActivity = activity as ManageProductsActivity
            manageProductActivity.loadFragment(manageProductActivity.addProductFragment)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = ViewProductsFragment()
    }

    override fun onProductItemClick(product: Product) {
        TODO("Not yet implemented")
    }
}