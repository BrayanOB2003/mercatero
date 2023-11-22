package icesi.edu.co.mercatero.view.authentication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentSigninBinding
import icesi.edu.co.mercatero.viewmodel.authetication.AuthViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSigninBinding
    private lateinit var navController:NavController
    private val authViewModel =  AuthViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtCreateAccount?.setOnClickListener {
            navController.navigate(R.id.action_SignInFragment_to_signUpTypeFragment)
        }

        binding.signInButton?.setOnClickListener{
            signIn(binding.textInputEmail.editText?.text.toString(),
                binding.textInputPassword.editText?.text.toString())
        }
    }

    private fun signIn(email: String, password: String){
        authViewModel.signInClient(email, password)
        authViewModel.authStateLV.observe(viewLifecycleOwner) { authState ->
            Log.e(">>>", email)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}