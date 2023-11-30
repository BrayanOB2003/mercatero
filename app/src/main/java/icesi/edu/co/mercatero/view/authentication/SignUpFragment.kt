package icesi.edu.co.mercatero.view.authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentSignUpBinding
import icesi.edu.co.mercatero.databinding.FragmentSigninBinding
import icesi.edu.co.mercatero.view.home.HomeActivity
import icesi.edu.co.mercatero.viewmodel.authetication.AuthViewModel

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val authViewModel: AuthViewModel by activityViewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpNextButton.setOnClickListener {
            signUpFieldsValidation()
        }

        binding.txtCreateAccount.setOnClickListener{
            val authActivity = activity as AuthActivity
            authActivity.loadFragment(authActivity.signInFragment)
        }
    }

    private fun signUpPrimaryData(names: String, lastNames: String, email: String, phoneNumber: String){
        authViewModel.signUpPrimaryData(names, lastNames, email, phoneNumber)
        val authActivity = activity as AuthActivity
        authActivity.loadFragment(authActivity.signUpPasswordFragment)
    }

    private fun signUpFieldsValidation(){
        var nameField = binding.nameTextInput.editText?.text
        var lastNameField = binding.lastNameTextInput.editText?.text
        var email = binding.emailTextInput.editText?.text
        var numberPhone = binding.phoneTextInput.editText?.text

        if(nameField?.isEmpty() == true){
            binding.nameTextInput.editText?.error = getText(R.string.error_empty_field)
        } else if(lastNameField?.isEmpty() == true){
            binding.lastNameTextInput.editText?.error = getText(R.string.error_empty_field)
        } else if(email?.isEmpty() == true) {
            binding.emailTextInput.editText?.error = getText(R.string.error_empty_field)
        } else if(numberPhone?.isEmpty() == true){
            binding.phoneTextInput.editText?.error = getText(R.string.error_empty_field)
        } else {
            signUpPrimaryData(nameField.toString(), lastNameField.toString(), email.toString(), numberPhone.toString())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpFragment()
    }
}