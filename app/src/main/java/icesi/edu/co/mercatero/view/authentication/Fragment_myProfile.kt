package icesi.edu.co.mercatero.view.authentication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import icesi.edu.co.mercatero.MainActivity
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.FragmentMyProfileBinding

class Fragment_myProfile : Fragment() {
    // TODO: Rename and change types of parameters

    val binding: FragmentMyProfileBinding by lazy {
        FragmentMyProfileBinding.inflate(layoutInflater);
    }
    private lateinit var mainImageUri: Uri
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val galLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), ::onGalleryResult

        )

        //binding.pfpImageView.setOnClickListener {

        //  val intent = Intent(Intent.ACTION_GET_CONTENT)
        //intent.type = "image/*"
        //galLauncher.launch(intent)

        //}


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance(mainActivity: MainActivity) = Fragment_myProfile().apply {
            //   this.mainActivity = mainActivity
            // }


            fun newInstance(): Fragment_myProfile {
                return Fragment_myProfile()
            }
        }
    }

    private fun onGalleryResult(activityResult: ActivityResult) {

        if (activityResult.resultCode == AppCompatActivity.RESULT_OK) {
            val uri = activityResult.data?.data
            mainImageUri = uri!!
            //val name = activityResult.data.data.
            uri?.let {
                Log.e(">>>", it.toString())
                binding.pfpImageView.setImageURI(uri)
                mainImageUri = uri
            }


        }
    }

    /*fun newInstance(mainActivity: MainActivity):Fragment_myProfile{

        return Fragment_myProfile().apply {
            this.mainActivity = mainActivity
        }
    }


     */

}