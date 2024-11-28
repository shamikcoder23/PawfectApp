package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider

class BirdScrollActivity : AppCompatActivity() {
    private lateinit var birdItem : ArrayList<Pet>

    private lateinit var account: Auth0

    private var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bird_scroll)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        account = Auth0( getString(R.string.auth0_clientId), getString(R.string.auth0_domain) )

        val rev : RecyclerView = findViewById(R.id.rev)
        birdItem = ArrayList()
        birdItem.add(Pet(R.drawable.bird_1, "Charlie, Parakeet", getString(R.string.dummy)))
        birdItem.add(Pet(R.drawable.bird_2, "King, Lovebird", getString(R.string.dummy)))
        birdItem.add(Pet(R.drawable.bird_3, "Dorothy, Cockatiel", getString(R.string.dummy)))
        birdItem.add(Pet(R.drawable.bird_4, "Bond, Parrot", getString(R.string.dummy)))
        birdItem.add(Pet(R.drawable.bird_5, "Nathan, Bird 5", getString(R.string.dummy)))
        birdItem.add(Pet(R.drawable.bird_6, "Hunt, Bird 6", getString(R.string.dummy)))
        birdItem.add(Pet(R.drawable.bird_7, "Tango, The Parrot", getString(R.string.dummy)))
        val adapter = PetAdapter(birdItem)
        val linLayManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rev.layoutManager = linLayManager
        rev.adapter = adapter

        adapter.setOnClickListener(object : PetAdapter.OnClickListener {
            override fun onClick(position: Int, model: Pet) {
                val bundle = Bundle()
                bundle.putInt("image", model.getImg())
                bundle.putString("name", model.getName())
                bundle.putString("desc", model.getDesc())
                val myIntent = Intent(this@BirdScrollActivity, PetDetailsActivity::class.java)
                myIntent.putExtras(bundle)
                startActivity(myIntent)
            }
        })

        val homeBtn : ImageButton = findViewById(R.id.homeBtn)
        homeBtn.setOnClickListener {
            startActivity(Intent(this, LandingActivity::class.java))
        }
        val accountBtn : ImageButton = findViewById(R.id.accountBtn)
        accountBtn.setOnClickListener{
            Toast.makeText(this, R.string.none, Toast.LENGTH_LONG).show()
            // startActivity(Intent(this, AccountActivity::class.java))
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
                    Toast.makeText(this@BirdScrollActivity, getString(R.string.login_fail), Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(result: Void?) {
                    user = User()
                    startActivity(Intent(this@BirdScrollActivity, MainActivity::class.java))
                }
            }
        )
    }
}