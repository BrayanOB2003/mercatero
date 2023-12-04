package icesi.edu.co.mercatero.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import icesi.edu.co.mercatero.databinding.FragmentConfirmOrderBinding
import icesi.edu.co.mercatero.model.shop.Order
import icesi.edu.co.mercatero.viewmodel.OrderViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConfirmOrderFragment(private var orders: List<Order>) : Fragment() {

    private lateinit var binding: FragmentConfirmOrderBinding
    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            val subtotal = calculateProductsPrice(orders)
            binding.textViewAddress.text = orders[0].address
            binding.textViewAmount.text = (subtotal + 3500).toString()
            binding.textViewSubtotal.text = subtotal.toString()
            binding.textViewShip.text = "3500"
            binding.textViewDiscount.text = "0"
            binding.textViewTotal.text = (subtotal + 3500).toString()
            binding.buttonCreateOrder.setOnClickListener {
                lifecycleScope.launch {
                    orderViewModel.addOrders(orders)
                }
            }
        }
    }

    private suspend fun calculateProductsPrice(orders: List<Order>): Int {
        var subtotal = 0
        withContext(Dispatchers.Default) {
            subtotal = orderViewModel.calculatePriceOrders(orders)
        }
        Log.e(">>> subtotal", subtotal.toString())
        return subtotal
    }

    companion object {
        @JvmStatic
        fun newInstance(orders: List<Order>) : ConfirmOrderFragment {
            return ConfirmOrderFragment(orders)
        }
    }
}