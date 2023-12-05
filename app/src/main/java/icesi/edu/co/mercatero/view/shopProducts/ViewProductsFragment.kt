package icesi.edu.co.mercatero.view.shopProducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.mercatero.view.adapters.ProductAdapter
import icesi.edu.co.mercatero.databinding.FragmentManageViewProductsBinding
import icesi.edu.co.mercatero.model.Product
import icesi.edu.co.mercatero.viewmodel.product.ProductViewModel

class ViewProductsFragment : Fragment() {

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

        //val products = arrayOf(Product("asdasdasd", "Prueba", "Prueba desc", 100000.0))

        //val adapter = ProductAdapter(products)
        //binding.productList.adapter = adapter
        //binding.productList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.buttonBack.setOnClickListener{
            activity?.finish()
        }

        binding.buttonAddProduct.setOnClickListener {
            val manageProductActivity = activity as ManageProductsActivity
            manageProductActivity.loadFragment(manageProductActivity.addProductFragment)
        }

        binding.addFloatingButton.setOnClickListener{
            val manageProductActivity = activity as ManageProductsActivity
            manageProductActivity.loadFragment(manageProductActivity.addProductFragment)
        }

        var products = ArrayList<Product>()

        if(products.size == 0){
            binding.productList.isVisible = false
        } else {
            binding.buttonAddProduct.isVisible = false
            binding.addFloatingButton.isVisible = true
        }


    }

    companion object {
        @JvmStatic
        fun newInstance() = ViewProductsFragment()
    }
}