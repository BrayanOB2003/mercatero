package icesi.edu.co.mercatero.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import icesi.edu.co.mercatero.databinding.FragmentHomeBinding
import icesi.edu.co.mercatero.view.adapters.home.ButtonAdapter
import icesi.edu.co.mercatero.view.adapters.home.ShopAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycleView()
    }

    private fun initRecycleView(){

        val buttons = arrayOf("Botón 1", "Botón 2", "Botón 3", "Botón 4", "Botón 5","Botón 1", "Botón 2", "Botón 3", "Botón 4", "Botón 5")

        binding.categoriesRecyclerView.adapter = ButtonAdapter(buttons)
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val data = arrayOf(
            Pair("gs://mercatero.appspot.com/shop/shop_test.png", "Tiendita la esquinita"),
            Pair("gs://mercatero.appspot.com/shop/shop_test2.png", "Tienda los semanales"),
            Pair("gs://mercatero.appspot.com/shop/shop_test.png", "Tiendita la esquinita"),
            Pair("gs://mercatero.appspot.com/shop/shop_test2.png", "Tienda los semanales"),
            Pair("gs://mercatero.appspot.com/shop/shop_test.png", "Tiendita la esquinita"),
            Pair("gs://mercatero.appspot.com/shop/shop_test2.png", "Tienda los semanales")
        )

        binding.shopsRecyclerView.adapter = ShopAdapter(requireContext(), data)
        binding.shopsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}