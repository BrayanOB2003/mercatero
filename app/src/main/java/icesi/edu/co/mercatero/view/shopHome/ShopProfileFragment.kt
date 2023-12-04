package icesi.edu.co.mercatero.view.shopHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
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
                Glide.with(requireContext())
                    .load(it.imageURL)
                    .into(binding.imageShopProfile)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance() = ShopProfileFragment()
    }
}