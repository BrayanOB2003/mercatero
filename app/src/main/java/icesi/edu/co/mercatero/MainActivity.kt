package icesi.edu.co.mercatero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import icesi.edu.co.mercatero.view.authentication.AuthActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}