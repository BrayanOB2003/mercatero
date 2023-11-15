package icesi.edu.co.mercatero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import icesi.edu.co.mercatero.databinding.ActivityMainBinding
import icesi.edu.co.mercatero.view.authentication.EntryFragment
import icesi.edu.co.mercatero.view.authentication.SignUpFragment
import icesi.edu.co.mercatero.view.authentication.myProfile

class MainActivity : AppCompatActivity() {

    lateinit var entryFragment: EntryFragment
    lateinit var signUpFragment: SignUpFragment
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(binding.root.context, myProfile::class.java))



    }

    fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
       // transaction.replace(binding.fragmentCV.id, fragment)
        transaction.commit()
    }
    fun showFragment(fragment: Fragment, fragmentContainerID:Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(fragmentContainerID, fragment)
        transaction.commit()
    }


}