package icesi.edu.co.mercatero.view.shopHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentShopProfileBinding
import icesi.edu.co.mercatero.viewmodel.home.ShopProfileViewModel

class ShopProfileFragment : Fragment() {

    lateinit var binding: FragmentShopProfileBinding

    private lateinit var shopProfileViewModel: ShopProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopProfileBinding.inflate(inflater, container, false)
        shopProfileViewModel = ShopProfileViewModel()
        shopProfileViewModel.getShopData()
        shopProfileViewModel.shop.observe(this) {
            binding.textViewShopName.text = it.name
            binding.textViewShopEmail.text = it.email
            if (!it.imageURL.isNullOrEmpty()) {
                val storageReference: StorageReference = FirebaseStorage.getInstance()
                    .getReferenceFromUrl(it.imageURL!!)
                storageReference.downloadUrl.addOnSuccessListener { uri ->
                    Glide.with(requireContext())
                        .load(uri)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(binding.imageShopProfile)
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonBackShopProfile.setOnClickListener {

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShopProfileFragment()
    }
}