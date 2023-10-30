package icesi.edu.co.mercatero.view.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentEntryBinding
import icesi.edu.co.mercatero.viewmodel.authetication.AuthViewModel

class EntryFragment : Fragment() {

    private lateinit var binding: FragmentEntryBinding
    private val authViewModel = AuthViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEntryBinding.inflate(inflater, container, false)
        authViewModel.signInValidation()

        if(authViewModel.authStateLV.value?.isAuth == true){
            findNavController().navigate(R.id.action_EntryFragment_to_HomeActivity)
        } else {
            findNavController().navigate(R.id.action_EntryFragment_to_SecondFragment)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): EntryFragment {
            return EntryFragment()
        }
    }
}