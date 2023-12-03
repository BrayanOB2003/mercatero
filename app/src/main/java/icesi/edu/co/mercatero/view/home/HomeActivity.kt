package icesi.edu.co.mercatero.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.ActivityHomeBinding
import icesi.edu.co.mercatero.view.authentication.SignUpShopFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

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
            R.id.navigation_home -> selectedFragment = HomeFragment()
            R.id.navigation_orders -> selectedFragment = OrdersFragment()
            R.id.navigation_favorites -> selectedFragment = FavoritesFragment()
            R.id.navigation_profile -> selectedFragment = ProfileFragment()
        }
        return try {
            selectedFragment?.let { loadFragment(it) }
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}