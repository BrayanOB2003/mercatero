package icesi.edu.co.mercatero.view.authentication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.MyProfileBinding

class myProfile: AppCompatActivity() {

    val binding: MyProfileBinding by lazy {
        MyProfileBinding.inflate(layoutInflater)
    }

    private lateinit var mainImageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)
        setContentView(R.layout.my__profile)
        val galLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), ::onGalleryResult

        )

       val intent = Intent(Intent.ACTION_GET_CONTENT)
       intent.type = "image/*"
       galLauncher.launch(intent)
       binding.pfpIV.setOnClickListener{
            Log.d("Test","Hace click")
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            galLauncher.launch(intent)

        }
        binding.aboutMeCL.setOnClickListener {
            Log.d("Test","Hace Click en el CL")
//            startActivity(Intent(binding.root.context, myProfile::class.java))

        }
        binding.buttonBack.setOnClickListener {
            Log.d("Test","Hace Click en el Back")
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
                mainImageUri = uri
                Toast.makeText(applicationContext,uri.toString(),Toast.LENGTH_SHORT).show();

            }


        }
    }


}