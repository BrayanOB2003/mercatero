package icesi.edu.co.mercatero.view.home.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import icesi.edu.co.mercatero.databinding.FragmentProfileBinding
import icesi.edu.co.mercatero.model.user.Client
import icesi.edu.co.mercatero.view.authentication.AuthActivity
import icesi.edu.co.mercatero.viewmodel.home.HomeViewModel

class ProfileFragment(currentClient: Client) : Fragment() {

    private val client = currentClient
    private lateinit var  binding: FragmentProfileBinding
    private lateinit var mainImageUri: Uri
    private val myProfileViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val galLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), ::onGalleryResult
        )

        binding.nameTV.text = client.name
        binding.emailTV.text = client.email
        if (!client.imageURL.isNullOrEmpty()) {
            Glide.with(requireContext())
                .load(client.imageURL)
                .into(binding.pfpIV)
        }

        binding.pfpIV.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            galLauncher.launch(intent)
        }
        binding.aboutMeButton.setOnClickListener {

        }
        binding.signOffButton.setOnClickListener {
            myProfileViewModel.signOut()
            val intent = Intent(activity, AuthActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun onGalleryResult(activityResult: ActivityResult) {

        if (activityResult.resultCode == AppCompatActivity.RESULT_OK) {
            val uri = activityResult.data?.data
            mainImageUri = uri!!
            uri.let {
                binding.pfpIV.setImageURI(uri)
                myProfileViewModel.updateProfileImage(uri)
                Toast.makeText(requireContext(),uri.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(currentClient: Client) = ProfileFragment(currentClient)
    }
}