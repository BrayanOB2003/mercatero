package icesi.edu.co.mercatero.view.authentication

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentEntryBinding
import icesi.edu.co.mercatero.viewmodel.authetication.AuthViewModel


class EntryFragment : Fragment() {

    private lateinit var binding: FragmentEntryBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInValidation()
    }

    private fun signInValidation(){

        authViewModel.authStateLV.observe(viewLifecycleOwner) { state ->
            if(state.isAuth == true){

            } else {
                val authActivity = activity as AuthActivity
                authActivity.loadFragment(authActivity.signInFragment)
            }
        }
        authViewModel.signInValidation()
    }

    companion object {
        @JvmStatic
        fun newInstance() = EntryFragment()
    }
}