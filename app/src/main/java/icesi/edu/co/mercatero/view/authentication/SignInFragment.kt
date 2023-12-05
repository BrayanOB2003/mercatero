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
import icesi.edu.co.mercatero.databinding.FragmentSigninBinding
import icesi.edu.co.mercatero.model.enumeration.UserType
import icesi.edu.co.mercatero.view.home.HomeActivity
import icesi.edu.co.mercatero.view.shopHome.ShopHomeActivity
import icesi.edu.co.mercatero.viewmodel.authetication.AuthViewModel

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSigninBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var loadingDialog: AlertDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.signInValidation()
        showLoadingScreen()

        binding.txtCreateAccount?.setOnClickListener {
            val authActivity = activity as AuthActivity
            authActivity.loadFragment(authActivity.signUpTypeFragment)
        }

        signInObserve()
        binding.signInButton?.setOnClickListener {
            signInFieldsValidation()
        }
    }

    private fun signInFieldsValidation(){
        var email = binding.textInputEmail.editText?.text
        var password = binding.textInputPassword.editText?.text

        if (email?.isEmpty() == true) {
            binding.textInputEmail.editText?.error = getText(R.string.error_empty_field)
        } else if(password?.isEmpty() == true){
            binding.textInputPassword.editText?.error = getText(R.string.error_empty_field)
        } else {
            showLoadingScreen()
            authViewModel.signInClient(email.toString(), password.toString())
            authViewModel.reloadState()
        }
    }

    private fun signInObserve(){
        authViewModel.authStateLV.observe(viewLifecycleOwner) { state ->
            if (state.isAuth == true) {
                if (state.userType == UserType.CLIENT) {
                    startActivity(Intent(requireContext(), HomeActivity::class.java))
                    requireActivity().finish()
                } else if(state.userType == UserType.SHOP){
                    startActivity(Intent(requireContext(), ShopHomeActivity::class.java))
                    requireActivity().finish()
                }
            } else if(state.isAuth == false){
                binding.textInputPassword.editText?.error = getText(R.string.error_text_field)
                loadingDialog.dismiss()
            }
        }
    }

    private fun showLoadingScreen() {
        val loadingScreen = LayoutInflater.from(requireContext()).inflate(R.layout.loading_screen, null)
        val progressBar = loadingScreen.findViewById<ProgressBar>(R.id.progressBar)
        val loadingMessage = loadingScreen.findViewById<TextView>(R.id.loadingMessage)
        loadingMessage.text = getText(R.string.singin_message)
        loadingDialog = AlertDialog.Builder(requireContext())
            .setView(loadingScreen)
            .setCancelable(false)
            .create()
        loadingDialog.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}