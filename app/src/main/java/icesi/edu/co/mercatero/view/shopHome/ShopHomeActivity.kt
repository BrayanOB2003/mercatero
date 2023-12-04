package icesi.edu.co.mercatero.view.shopHome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.ActivityHomeBinding
import icesi.edu.co.mercatero.databinding.ActivityShopHomeBinding
import icesi.edu.co.mercatero.view.home.FavoritesFragment
import icesi.edu.co.mercatero.view.home.HomeFragment
import icesi.edu.co.mercatero.view.home.OrdersFragment
import icesi.edu.co.mercatero.view.home.ProfileFragment
import icesi.edu.co.mercatero.view.shopProducts.ManageProductsActivity

class ShopHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(OrderRequestedFragment())

        binding.storeButton.setOnClickListener{
            val intent = Intent(this, ManageProductsActivity::class.java)
            startActivity(intent)
        }
        binding.profileButton.setOnClickListener{

        }
    }

    private fun handleNavigationItemSelected(item: MenuItem): Boolean {
        var selectedFragment: Fragment? = null

        when (item.itemId) {
            R.id.navigation_requestedOrder -> selectedFragment = OrderRequestedFragment()
            R.id.navigation_inPreparation -> selectedFragment = OrderInProgressFragment()
            R.id.navigation_toDeliver -> selectedFragment = OrdeOnFinishedFragment()
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
            .replace(binding.container.id, fragment)
            .commit()
    }
}