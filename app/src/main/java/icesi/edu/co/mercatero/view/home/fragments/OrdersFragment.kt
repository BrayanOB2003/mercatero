package icesi.edu.co.mercatero.view.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.mercatero.databinding.FragmentOrdersBinding
import icesi.edu.co.mercatero.view.adapters.home.OrderAdapter
import icesi.edu.co.mercatero.viewmodel.home.HomeViewModel

class OrdersFragment : Fragment() {

    lateinit var binding: FragmentOrdersBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        homeViewModel = HomeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
    }

    private fun initRecycleView() {
        homeViewModel.getOrdersOfUser()
        homeViewModel.orders.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) {
                binding.recyclerUserOrders.adapter = OrderAdapter(it)
            }
        }
        binding.recyclerUserOrders.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrdersFragment()
    }
}