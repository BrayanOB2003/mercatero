package icesi.edu.co.mercatero.view.shopProducts

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentManageAddProductBinding
import icesi.edu.co.mercatero.viewmodel.product.ProductViewModel
import kotlinx.coroutines.launch

class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentManageAddProductBinding
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private val productViewModel: ProductViewModel by activityViewModels()
    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageAddProductBinding.inflate(inflater, container, false)
        galleryLauncher = registerForActivityResult(StartActivityForResult(), ::onGalleryResult)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            addProductFieldsValidation()
        }
        binding.productImage.setOnLongClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            galleryLauncher.launch(intent)
            true
        }
    }

    private fun addProductFieldsValidation() {
        val name = binding.editTextProductName.editText?.text.toString()
        val description = binding.editTextProductDescription.editText?.text.toString()
        val price = binding.editTextProductPrice.editText?.text.toString()
        if (name.isEmpty()) {
            binding.editTextProductName.editText?.error = getText(R.string.error_empty_field)
        }
        if (description.isEmpty()) {
            binding.editTextProductDescription.editText?.error = getText(R.string.error_empty_field)
        }
        if (price.isEmpty() || price.toDouble().isNaN()) {
            binding.editTextProductPrice.editText?.error = getText(R.string.error_empty_field)
        }
        if (name.isNotEmpty() && description.isNotEmpty() && !price.toDouble().isNaN() && uri.toString().isNotEmpty()) {
            lifecycleScope.launch {
                uri?.let {
                    productViewModel.addProduct(name, description, price.toDouble(), it)
                    //val manageProductsActivity = activity as ManageProductsActivity
                    //manageProductsActivity.loadFragment(manageProductsActivity.viewProductsFragment)
                }
            }
        }
    }

    private fun onGalleryResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            uri = result.data?.data
            binding.productImage.setImageURI(uri)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddProductFragment()
    }
}