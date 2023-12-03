package icesi.edu.co.mercatero.view.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import icesi.edu.co.mercatero.databinding.FragmentAddProductBinding
import icesi.edu.co.mercatero.viewmodel.product.ProductViewModel

class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentAddProductBinding
    private val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            addProductEvent()
        }
    }

    private fun addProductEvent() {
        val name = binding.editTextProductName.editText?.text.toString()
        val description = binding.editTextProductDescription.editText?.text.toString()
        val price = binding.editTextProductPrice.editText?.text.toString().toDouble()

        if (name.isNotEmpty() && description.isNotEmpty() && price > 0) {
            productViewModel.addProductPrimaryData(name, description, price)
            productViewModel.addProduct()
            Log.d(">>>", "Producto creado")
        } else {
            Log.e(">>>", "Error al crear el producto.")
        }

        binding.editTextProductName.editText?.text

    }
}