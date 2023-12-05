package icesi.edu.co.mercatero.view.shopHome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return inflater.inflate(R.layout.fragment_order_on_finished, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrderOnFinishedFragment()
    }

    override fun onClickUpdateStatus(order_id: String) {
        shopViewModel.updateOrderStatus(order_id)
    }
}