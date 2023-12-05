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
import icesi.edu.co.mercatero.databinding.FragmentOrderOnFinishedBinding
import icesi.edu.co.mercatero.view.adapters.shopHome.OnClickOrderButton
import icesi.edu.co.mercatero.view.adapters.shopHome.OrderAdapter
import icesi.edu.co.mercatero.viewmodel.shopHome.ShopHomeViewModel

class OrderOnFinishedFragment : Fragment(), OnClickOrderButton {

    private lateinit var binding: FragmentOrderOnFinishedBinding
    private lateinit var shopViewModel: ShopHomeViewModel
  //  private lateinit var onClickOrderButton: OnClickOrderButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderOnFinishedBinding.inflate(layoutInflater, container, false)
        shopViewModel = ShopHomeViewModel()
        shopViewModel.getOrdersInDelivery("0x4bM1Y9HKh8psLZZPYe")
        shopViewModel.orders.observe(viewLifecycleOwner){

            binding.orderRecyclerView.adapter = OrderAdapter(it.orderInfo,it.listName,this)
            binding.orderRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)



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