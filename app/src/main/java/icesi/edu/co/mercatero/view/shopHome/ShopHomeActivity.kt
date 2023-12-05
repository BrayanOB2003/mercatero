package icesi.edu.co.mercatero.view.shopHome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.ActivityShopHomeBinding
import icesi.edu.co.mercatero.model.Shop
import icesi.edu.co.mercatero.view.shopProducts.ManageProductsActivity
import icesi.edu.co.mercatero.viewmodel.shopHome.ShopHomeViewModel

class ShopHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopHomeBinding
    private lateinit var viewModel:ShopHomeViewModel
    private lateinit var authShop: Shop
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopHomeBinding.inflate(layoutInflater)
        viewModel = ShopHomeViewModel()
        setContentView(binding.root)
        getAuthShop()

        loadFragment(OrderRequestedFragment())

        binding.storeButton.setOnClickListener{
            val intent = Intent(this, ManageProductsActivity::class.java)
            startActivity(intent)
        }
        binding.profileButton.setOnClickListener{

        }

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            handleNavigationItemSelected(menuItem)
        }
    }

    private fun getAuthShop(){
        viewModel.getAuthUser()
        viewModel.shopAuth.observe(this){
            binding.welcomeText.text = binding.welcomeText.text.toString() + viewModel.shopAuth.value?.name
        }
    }

    private fun handleNavigationItemSelected(item: MenuItem): Boolean {
        var selectedFragment: Fragment? = null

        when (item.itemId) {
            R.id.navigation_requestedOrder -> selectedFragment = OrderRequestedFragment()
            R.id.navigation_inPreparation -> selectedFragment = OrderInProgressFragment()
            R.id.navigation_toDeliver -> selectedFragment = OrderOnFinishedFragment()
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