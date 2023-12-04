package icesi.edu.co.mercatero.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.ActivityHomeBinding
import icesi.edu.co.mercatero.model.shop.Order

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
            R.id.navigation_orders -> selectedFragment = ConfirmOrderFragment(
                listOf(
                    Order("p1lSI3Bs4nflEuW2Ip8Cgx0Kxhp1", "6sGET5VP1oU1S7fZNpdK", "calle 40 #59-05", mapOf(
                        "Lyt6iNNVKaXoL49ngmxQ" to 2
                    )),
                    Order(
                        "p1lSI3Bs4nflEuW2Ip8Cgx0Kxhp1", "0x4bM1Y9HKh8psLZZPYe", "calle 40 #59-05", mapOf(
                            "504eBDFHm6uI0I0lNJeQ" to 2,

                            "Lyt6iNNVKaXoL49ngmxQ" to 3
                        )
                    )
                )
            )
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