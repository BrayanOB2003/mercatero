package icesi.edu.co.mercatero.view.authentication

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentSignUpBinding
import icesi.edu.co.mercatero.databinding.FragmentSignUpShopBinding
import icesi.edu.co.mercatero.viewmodel.authetication.AuthViewModel

class SignUpShopFragment : Fragment() {

    private lateinit var binding: FragmentSignUpShopBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpNextButton.setOnClickListener {
            signUpFieldsValidation()
        }
    }

    private fun signUpPrimaryData(names: String, lastNames: String, email: String, phoneNumber: String){
        authViewModel.signUpPrimaryDataShop(names, lastNames, email, phoneNumber)
        val authActivity = activity as AuthActivity
        authActivity.loadFragment(authActivity.signUpShopPasswordFragment)
    }

    private fun signUpFieldsValidation(){
        var nameField = binding.nameTextInput.editText?.text
        var addressField = binding.addressTextInput.editText?.text
        var email = binding.emailTextInput.editText?.text
        var numberPhone = binding.phoneTextInput.editText?.text

        if(!Patterns.EMAIL_ADDRESS.matcher(email?.toString()).matches()) {
            binding.emailTextInput.editText?.error = getText(R.string.singUp_invalid_email)
        }

        if(nameField?.isEmpty() == true){
            binding.nameTextInput.editText?.error = getText(R.string.error_empty_field)
        } else if(addressField?.isEmpty() == true){
            binding.addressTextInput.editText?.error = getText(R.string.error_empty_field)
        } else if(email?.isEmpty() == true) {
            binding.emailTextInput.editText?.error = getText(R.string.error_empty_field)
        } else if(numberPhone?.isEmpty() == true){
            binding.phoneTextInput.editText?.error = getText(R.string.error_empty_field)
        } else {
            signUpPrimaryData(nameField.toString(), addressField.toString(), email.toString(), numberPhone.toString())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpShopFragment()
    }
}