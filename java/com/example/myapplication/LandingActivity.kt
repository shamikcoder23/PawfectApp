package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider

class LandingActivity : AppCompatActivity() {

    private lateinit var account: Auth0

    private var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_landing)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        account = Auth0( getString(R.string.auth0_clientId), getString(R.string.auth0_domain) )
        val name = intent.extras?.getString("username")
        val email = intent.extras?.getString("email")

        val dogBtn : ImageButton = findViewById(R.id.dogBtn)
        dogBtn.setOnClickListener {
            startActivity(Intent(this, DogScrollActivity::class.java))
        }
        val catBtn : ImageButton = findViewById(R.id.catBtn)
        catBtn.setOnClickListener {
            startActivity(Intent(this, CatScrollActivity::class.java))
        }
        val birdBtn : ImageButton = findViewById(R.id.birdBtn)
        birdBtn.setOnClickListener {
            startActivity(Intent(this, BirdScrollActivity::class.java))
        }
        val fishBtn : ImageButton = findViewById(R.id.fishBtn)
        fishBtn.setOnClickListener {
            startActivity(Intent(this, FishScrollActivity::class.java))
        }
        val plantBtn : ImageButton = findViewById(R.id.plantBtn)
        plantBtn.setOnClickListener {
            Toast.makeText(this, "Nothing to show at present", Toast.LENGTH_LONG).show()
        }

        val homeBtn : ImageButton = findViewById(R.id.homeBtn)
        homeBtn.setOnClickListener {
            startActivity(Intent(this, LandingActivity::class.java))
        }
        val accountBtn : ImageButton = findViewById(R.id.accountBtn)
        accountBtn.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("username", name)
            bundle.putString("email", email)
            val myIntent = Intent(this, AccountActivity::class.java)
            myIntent.putExtras(bundle)
            startActivity(myIntent)
        }
        val basketBtn : ImageButton = findViewById(R.id.basketBtn)
        basketBtn.setOnClickListener{
            startActivity(Intent(this, BasketActivity::class.java))
        }
        val logoutBtn : ImageButton = findViewById(R.id.logoutBtn)
        logoutBtn.setOnClickListener{
            logout()
        }
    }
    private fun logout () {
        WebAuthProvider.logout(account).withScheme(getString(R.string.auth0_scheme)).start(
            this, object : Callback<Void?, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    Toast.makeText(this@LandingActivity, getString(R.string.login_fail), Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(result: Void?) {
                    user = User()
                    startActivity(Intent(this@LandingActivity, MainActivity::class.java))
                }
            }
        )
    }
}