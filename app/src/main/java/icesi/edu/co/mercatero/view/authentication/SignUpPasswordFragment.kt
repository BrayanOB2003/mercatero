package icesi.edu.co.mercatero.view.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentSignUpBinding

class SignUpPasswordFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpPasswordFragment()
    }
}