package icesi.edu.co.mercatero.view.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentProfileBinding
import icesi.edu.co.mercatero.databinding.MyProfileBinding
import icesi.edu.co.mercatero.viewmodel.home.ProfileViewModel

class ProfileFragment : Fragment() {

    lateinit var  binding: FragmentProfileBinding

    private lateinit var mainImageUri: Uri
    private lateinit var myProfileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

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
        return binding.root
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
                Toast.makeText(requireContext(),uri.toString(), Toast.LENGTH_SHORT).show();
            }


        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}