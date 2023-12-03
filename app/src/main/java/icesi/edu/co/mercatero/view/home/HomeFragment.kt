package icesi.edu.co.mercatero.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentHomeBinding
import icesi.edu.co.mercatero.view.adapters.ButtonAdapter

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

        val buttons = arrayOf("Botón 1", "Botón 2", "Botón 3", "Botón 4", "Botón 5","Botón 1", "Botón 2", "Botón 3", "Botón 4", "Botón 5")

        val adapter = ButtonAdapter(requireContext(), buttons)
        binding.listView.adapter = adapter
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}