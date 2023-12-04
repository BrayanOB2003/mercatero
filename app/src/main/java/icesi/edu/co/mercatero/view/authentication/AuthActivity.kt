package icesi.edu.co.mercatero.view.authentication

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAuthBinding

    private val entryFragment by lazy {
        EntryFragment.newInstance()
    }

    val signInFragment by lazy {
        SignInFragment.newInstance()
    }

    val signUpFragment by lazy {
        SignUpFragment.newInstance()
    }

    val signUpTypeFragment by lazy {
        SignUpTypeFragment.newInstance()
    }

    val signUpPasswordFragment by lazy {
        SignUpPasswordFragment.newInstance()
    }

    val signUpShopPasswordFragment by lazy {
        SignUpShopPasswordFragment.newInstance()
    }

    val signUpShopFragment by lazy {
        SignUpShopFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(signInFragment)
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment).commit()
    }
}