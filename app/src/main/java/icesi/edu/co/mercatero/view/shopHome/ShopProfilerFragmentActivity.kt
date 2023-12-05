package icesi.edu.co.mercatero.view.shopHome

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.ActivityShopProfilerFragmentBinding
import icesi.edu.co.mercatero.databinding.FragmentShopProfileBinding
import icesi.edu.co.mercatero.view.authentication.AuthActivity
import icesi.edu.co.mercatero.view.shopProducts.ManageProductsActivity
import icesi.edu.co.mercatero.viewmodel.home.ShopProfileViewModel

class ShopProfilerFragmentActivity : AppCompatActivity() {

    lateinit var binding: FragmentShopProfileBinding
    private lateinit var mainImageUri: Uri
    private lateinit var shopProfileViewModel: ShopProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentShopProfileBinding.inflate(layoutInflater)
        shopProfileViewModel = ShopProfileViewModel()
        setContentView(binding.root)

        val galLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), ::onGalleryResult
        )

        binding.buttonBackShopProfile.setOnClickListener {
            val intent = Intent(this, ShopHomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        shopProfileViewModel.getProfileData()
        shopProfileViewModel.shop.observe(this){

            binding.textViewShopName.text = it.name
            binding.textViewShopEmail.text = it.email
            if (!it.imageURL.isNullOrEmpty()) {
                Glide.with(this)
                    .load(it.imageURL)
                    .into(binding.imageShopProfile)
            }
        }
        binding.imageShopProfile.setOnClickListener{
            Log.d("Test","Hace click")
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            galLauncher.launch(intent)
        }
        binding.aboutMeButton.setOnClickListener {
            Log.d("Test","Hace Click en el CL")

        }
        binding.signOffButton.setOnClickListener {
            shopProfileViewModel.signOut()
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun onGalleryResult(activityResult: ActivityResult) {

        if (activityResult.resultCode == AppCompatActivity.RESULT_OK) {
            Log.d("Test","Imagen seleccionada")
            val uri = activityResult.data?.data
            mainImageUri = uri!!
            // val name = activityResult.data.data.
            Log.d("Test",mainImageUri.toString())
            uri?.let {
                Log.e(">>>", it.toString())
                binding.imageShopProfile.setImageURI(uri)
                shopProfileViewModel.updateProfileImage(uri)
                Toast.makeText(this,uri.toString(), Toast.LENGTH_SHORT).show();
            }


        }
    }
}