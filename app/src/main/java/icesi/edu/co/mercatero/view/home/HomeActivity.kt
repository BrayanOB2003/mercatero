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

        when (item.itemId) {
            R.id.navigation_home -> loadFragment(BackStack.HOME.name) ?: loadFragment(HomeFragment(), BackStack.HOME.name)
            R.id.navigation_orders -> loadFragment(BackStack.ORDERS.name) ?: loadFragment(OrdersFragment(), BackStack.ORDERS.name)
            R.id.navigation_favorites -> loadFragment(BackStack.FAVORITES.name) ?: loadFragment(FavoritesFragment(), BackStack.FAVORITES.name)
            R.id.navigation_profile -> loadFragment(BackStack.PROFILE.name) ?: viewModel.clientAuth.value?.let { ProfileFragment(it) }
                ?.let { loadFragment(it, BackStack.PROFILE.name) }
            else -> return false
        }

        return true
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