package icesi.edu.co.mercatero.view.authentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.MyProfileBinding

class myProfile: AppCompatActivity() {

    val binding: MyProfileBinding by lazy {
        MyProfileBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my__profile)


    }

}