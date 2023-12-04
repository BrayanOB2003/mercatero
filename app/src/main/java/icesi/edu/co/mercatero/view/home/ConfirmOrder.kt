package icesi.edu.co.mercatero.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import icesi.edu.co.mercatero.databinding.FragmentConfirmOrderBinding
import icesi.edu.co.mercatero.model.shop.Order

class ConfirmOrder(order: Order) : Fragment() {

    private lateinit var binding: FragmentConfirmOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(order: Order) : ConfirmOrder {
            return ConfirmOrder(order)
        }
    }
}