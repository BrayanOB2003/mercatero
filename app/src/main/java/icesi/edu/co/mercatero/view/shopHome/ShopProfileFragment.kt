package icesi.edu.co.mercatero.view.shopHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import icesi.edu.co.mercatero.databinding.FragmentShopProfileBinding

class ShopProfileFragment : Fragment() {

    private lateinit var binding: FragmentShopProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopProfileBinding.inflate(inflater, container, false)
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