package icesi.edu.co.mercatero.view.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import icesi.edu.co.mercatero.databinding.MyProfileBinding
import icesi.edu.co.mercatero.viewmodel.home.ProfileViewModel

class MyProfile: AppCompatActivity() {

    lateinit var  binding: MyProfileBinding

    private lateinit var mainImageUri: Uri
    private lateinit var myProfileViewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?){

        binding = MyProfileBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val galLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), ::onGalleryResult
        )
        //binding.nameTV.text = "Hola"
        myProfileViewModel = ProfileViewModel()
        myProfileViewModel.getProfileData()
        myProfileViewModel.client.observe(this){

            binding.nameTV.text = it.name
            binding.emailTV.text = it.email
            if (!it.imageURL.isNullOrEmpty()) {
                Glide.with(applicationContext)
                    .load(it.imageURL)
                    .into(binding.pfpIV)
            }

        }
    //   val intent = Intent(Intent.ACTION_GET_CONTENT)
     //  intent.type = "image/*"
     //  galLauncher.launch(intent)
       binding.pfpIV.setOnClickListener{
            Log.d("Test","Hace click")
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            galLauncher.launch(intent)
        }
        binding.aboutMeButton.setOnClickListener {
            Log.d("Test","Hace Click en el CL")
//            startActivity(Intent(binding.root.context, myProfile::class.java))

        }
        /*binding.buttonBack.setOnClickListener {
            Log.d("Test","Hace Click en el Back")
        }*/


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
                myProfileViewModel.updateProfileImage(uri)
                Toast.makeText(applicationContext,uri.toString(),Toast.LENGTH_SHORT).show();
            }


        }
    }



}