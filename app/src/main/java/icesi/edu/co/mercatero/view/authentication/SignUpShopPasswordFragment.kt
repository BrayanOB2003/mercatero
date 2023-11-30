package icesi.edu.co.mercatero.view.authentication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentSignUpBinding
import icesi.edu.co.mercatero.databinding.FragmentSignUpPasswordBinding
import icesi.edu.co.mercatero.view.home.HomeActivity
import icesi.edu.co.mercatero.viewmodel.authetication.AuthViewModel

class SignUpShopPasswordFragment : Fragment() {

    private lateinit var binding: FragmentSignUpPasswordBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var loadingDialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpObserve()

        binding.createAccountButton.setOnClickListener {
            createAccount()
        }
    }

    private fun signUpObserve(){
        authViewModel.authStateLV.observe(viewLifecycleOwner) { state ->
            if(state.isAuth == true){
                startActivity(Intent(requireContext(), HomeActivity::class.java))
                requireActivity().finish()
            } else if(state.isAuth == false) {
                val authActivity = activity as AuthActivity
                authActivity.loadFragment(authActivity.signUpShopFragment)
                loadingDialog.dismiss()
            }
        }
    }

    private fun signUp(password: String, passwordConfirmation: String){
        if(password == passwordConfirmation){
            showLoadingScreen()
            authViewModel.signupShop(password)
        } else {
            binding.confirmPasswordTextInput?.editText?.error = getText(R.string.error_password_differents)
        }
    }
    private fun createAccount(){
        var password = binding.passwordTextInput.editText?.text
        var passwordConfirmation = binding.confirmPasswordTextInput.editText?.text

        if(password?.isEmpty() == true){
            binding.passwordTextInput.editText?.error = getText(R.string.error_empty_field)
        } else if(passwordConfirmation?.isEmpty() == true){
            binding.confirmPasswordTextInput.editText?.error = getText(R.string.error_empty_field)
        } else {
            signUp(password.toString(), passwordConfirmation.toString())
        }

    }

    private fun showLoadingScreen() {
        val loadingScreen = LayoutInflater.from(requireContext()).inflate(R.layout.loading_screen, null)
        val progressBar = loadingScreen.findViewById<ProgressBar>(R.id.progressBar)
        val loadingMessage = loadingScreen.findViewById<TextView>(R.id.loadingMessage)

        // Configura el mensaje de carga según sea necesario
        loadingMessage.text = getText(R.string.singup_message)

        // Muestra la pantalla de carga
        loadingDialog = AlertDialog.Builder(requireContext())
            .setView(loadingScreen)
            .setCancelable(false)
            .create()

        loadingDialog.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpShopPasswordFragment()
    }
}