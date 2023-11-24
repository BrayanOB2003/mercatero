package icesi.edu.co.mercatero.view.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.ActivityAuthBinding
import icesi.edu.co.mercatero.databinding.FragmentEntryBinding
import icesi.edu.co.mercatero.databinding.FragmentSignUpTypeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpTypeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpTypeFragment : Fragment() {

    private lateinit var binding: FragmentSignUpTypeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpTypeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            val authActivity = activity as AuthActivity
            authActivity.loadFragment(authActivity.signInFragment)
        }

        binding.singUpClientButton.setOnClickListener {
            val authActivity = activity as AuthActivity
            authActivity.loadFragment(authActivity.signUpFragment)
        }

        binding.singUpDeliverButton.setOnClickListener {

        }

        binding.singUpShopButton.setOnClickListener {

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpTypeFragment()
    }
}