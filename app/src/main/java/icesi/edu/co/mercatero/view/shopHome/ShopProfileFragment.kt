package icesi.edu.co.mercatero.view.shopHome

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentShopProfileBinding
import icesi.edu.co.mercatero.view.authentication.AuthActivity
import icesi.edu.co.mercatero.viewmodel.home.ProfileViewModel
import icesi.edu.co.mercatero.viewmodel.home.ShopProfileViewModel

class ShopProfileFragment : Fragment() {

    lateinit var binding: FragmentShopProfileBinding
    private lateinit var mainImageUri: Uri
    private lateinit var shopProfileViewModel: ShopProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopProfileBinding.inflate(inflater, container, false)
        shopProfileViewModel = ShopProfileViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val galLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), ::onGalleryResult
        )

        binding.buttonBackShopProfile.setOnClickListener {

        }

        shopProfileViewModel.getProfileData()
        shopProfileViewModel.shop.observe(viewLifecycleOwner){

            binding.nameTV.text = it.name
            binding.emailTV.text = it.email
            if (!it.imageURL.isNullOrEmpty()) {
                Glide.with(requireContext())
                    .load(it.imageURL)
                    .into(binding.pfpIV)
            }
        }
        binding.pfpIV.setOnClickListener{
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
            val intent = Intent(activity, AuthActivity::class.java)
            startActivity(intent)
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
                binding.pfpIV.setImageURI(uri)
                shopProfileViewModel.updateProfileImage(uri)
                Toast.makeText(requireContext(),uri.toString(), Toast.LENGTH_SHORT).show();
            }


        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShopProfileFragment()
    }
}