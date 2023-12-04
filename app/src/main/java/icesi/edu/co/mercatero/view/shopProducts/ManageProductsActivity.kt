package icesi.edu.co.mercatero.view.shopProducts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import icesi.edu.co.mercatero.databinding.ActivityManageProductsBinding

class ManageProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageProductsBinding

    val viewProductsFragment by lazy {
       ViewProductsFragment.newInstance()
    }

    val addProductFragment by lazy {
        AddProductFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(viewProductsFragment)

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.manageProductContainer.id, fragment)
            .commit()
    }
}