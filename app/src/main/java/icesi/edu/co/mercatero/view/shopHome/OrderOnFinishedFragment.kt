package icesi.edu.co.mercatero.view.shopHome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentOrderOnFinishedBinding
import icesi.edu.co.mercatero.view.adapters.shopHome.OnClickOrderButton
import icesi.edu.co.mercatero.viewmodel.shopHome.ShopHomeViewModel

class OrderOnFinishedFragment : Fragment(), OnClickOrderButton {

    private lateinit var binding: FragmentOrderOnFinishedBinding
    private lateinit var shopViewModel: ShopHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderOnFinishedBinding.inflate(layoutInflater, container, false)
        shopViewModel = ShopHomeViewModel()
        shopViewModel.getOrdersInDelivery(Firebase.auth.currentUser!!.uid.toString())
        shopViewModel.orders.observe(viewLifecycleOwner){




        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrderOnFinishedFragment()
    }

    override fun onClickUpdateStatus(order_id: String) {
        shopViewModel.updateOrderStatus(order_id)
    }
}