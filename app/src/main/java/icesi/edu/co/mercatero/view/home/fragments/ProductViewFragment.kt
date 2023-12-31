package icesi.edu.co.mercatero.view.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentProductViewBinding
import icesi.edu.co.mercatero.model.Product
import icesi.edu.co.mercatero.viewmodel.OrderViewModel
import icesi.edu.co.mercatero.viewmodel.home.HomeViewModel

class ProductViewFragment(private var product: Product) : Fragment() {

    private lateinit var binding: FragmentProductViewBinding
    private val orderViewModel: HomeViewModel by activityViewModels()

    private var amount:Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductViewBinding.inflate(inflater, container, false)
        if (product.imageURL.isNotEmpty()) {
            val storageReference: StorageReference = FirebaseStorage.getInstance()
                .getReferenceFromUrl(product.imageURL)
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(requireContext())
                    .load(uri)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(binding.imageViewProduct)
            }
        }
        binding.textViewProductNameValue.text = product.name
        binding.textViewProductDescriptionValue.text = product.description
        binding.textViewProductPriceValue.text = product.price
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        orderViewModel.addProductToOrder(product, amount)
    }

    companion object {
        @JvmStatic
        fun newInstance(product: Product) : ProductViewFragment {
            return ProductViewFragment(product)
        }
    }
}