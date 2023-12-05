package icesi.edu.co.mercatero.view.shopHome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentOrderInProgressBinding
import icesi.edu.co.mercatero.databinding.FragmentOrdersBinding
import icesi.edu.co.mercatero.view.adapters.shopHome.OnClickOrderButton
import icesi.edu.co.mercatero.view.adapters.shopHome.OrderAdapter
import icesi.edu.co.mercatero.viewmodel.shopHome.ShopHomeViewModel

class OrderInProgressFragment : Fragment(), OnClickOrderButton {

    private lateinit var binding: FragmentOrderInProgressBinding
    private lateinit var shopViewModel: ShopHomeViewModel
   // private lateinit var onClickOrderButton: OnClickOrderButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderInProgressBinding.inflate(layoutInflater, container, false)
        shopViewModel = ShopHomeViewModel()
        shopViewModel.getOrdersInPreparation("0x4bM1Y9HKh8psLZZPYe")
        shopViewModel.orders.observe(viewLifecycleOwner){


            binding.orderRecyclerView.adapter = OrderAdapter(it.orderInfo,it.listName,this)
            binding.orderRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrderInProgressFragment()
    }

    override fun onClickUpdateStatus(order_id: String) {
        shopViewModel.updateOrderStatus(order_id)
    }
}