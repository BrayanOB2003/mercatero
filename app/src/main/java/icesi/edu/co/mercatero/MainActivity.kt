package icesi.edu.co.mercatero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import icesi.edu.co.mercatero.databinding.ActivityMainBinding
import icesi.edu.co.mercatero.view.authentication.AuthActivity

class MainActivity : AppCompatActivity() {

    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(binding.root.context, AuthActivity::class.java))
    }
}