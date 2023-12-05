package icesi.edu.co.mercatero.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import icesi.edu.co.mercatero.databinding.FragmentProductViewBinding
import icesi.edu.co.mercatero.model.Product
import icesi.edu.co.mercatero.viewmodel.OrderViewModel

class ProductViewFragment(private var product: Product) : Fragment() {

    private lateinit var binding: FragmentProductViewBinding
    private lateinit var orderViewModel: OrderViewModel

    private var amount = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductViewBinding.inflate(inflater, container, false)
        orderViewModel = OrderViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewProductName.text = product.name
        binding.textViewProductDescription.text = product.description
        binding.textViewProductPrice.text = product.price
        binding.buttonDecrease.setOnClickListener {
            decreaseAmount()
        }
        binding.buttonIncrease.setOnClickListener {
            increaseAmount()
        }
        binding.buttonAddProductToCart.setOnClickListener {
            addToCart()
        }
    }

    private fun decreaseAmount() {
        if (amount > 1) {
            amount--
            binding.textViewProductAmount.text = amount.toString()
        }
    }

    private fun increaseAmount() {
        amount++
        binding.textViewProductAmount.text = amount.toString()
    }

    private fun addToCart() {

    }

    companion object {
        @JvmStatic
        fun newInstance(product: Product) : ProductViewFragment {
            return ProductViewFragment(product)
        }
    }
}