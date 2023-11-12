package icesi.edu.co.mercatero.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import icesi.edu.co.mercatero.R
import icesi.edu.co.mercatero.databinding.ActivityAuthBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}