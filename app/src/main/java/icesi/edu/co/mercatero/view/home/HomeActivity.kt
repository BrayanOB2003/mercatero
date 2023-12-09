package icesi.edu.co.mercatero.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.ActivityHomeBinding
import icesi.edu.co.mercatero.view.home.fragments.BackStack
import icesi.edu.co.mercatero.view.home.fragments.FavoritesFragment
import icesi.edu.co.mercatero.view.home.fragments.HomeFragment
import icesi.edu.co.mercatero.view.home.fragments.OrdersFragment
import icesi.edu.co.mercatero.view.home.fragments.ProfileFragment
import icesi.edu.co.mercatero.viewmodel.home.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    val homeFragment by lazy {
        HomeFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            handleNavigationItemSelected(menuItem)
        }
    }

    private fun handleNavigationItemSelected(item: MenuItem): Boolean {
        var selectedFragment: Fragment? = null

        when (item.itemId) {
            R.id.navigation_home -> selectedFragment = loadFragment(BackStack.HOME.name) ?: HomeFragment()
            R.id.navigation_orders -> selectedFragment = loadFragment(BackStack.ORDERS.name) ?: OrdersFragment()
            R.id.navigation_favorites -> selectedFragment = loadFragment(BackStack.FAVORITES.name) ?: FavoritesFragment()
            R.id.navigation_profile -> selectedFragment = loadFragment(BackStack.PROFILE.name) ?: viewModel.clientAuth.value?.let { ProfileFragment(it) }
        }
        return try {
            selectedFragment?.let { loadFragment(it) }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .disallowAddToBackStack()
            .replace(binding.container.id, fragment)
            .commit()
    }
    fun loadFragment(fragment: Fragment, backStackId: String) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(backStackId)
            .replace(binding.container.id, fragment)
            .commit()
    }
    fun loadFragment(backStackId: String): Fragment? {
        return supportFragmentManager.findFragmentByTag(backStackId)
    }
}