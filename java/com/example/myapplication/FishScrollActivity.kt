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

class FishScrollActivity : AppCompatActivity() {
    private lateinit var fishItem : ArrayList<Pet>

    private lateinit var account: Auth0

    private var user = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fish_scroll)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        account = Auth0( getString(R.string.auth0_clientId), getString(R.string.auth0_domain) )

        val rev : RecyclerView = findViewById(R.id.rev)
        fishItem = ArrayList()
        fishItem.add(Pet(R.drawable.fish_1, "Destiny, Fish 1", getString(R.string.dummy)))
        fishItem.add(Pet(R.drawable.fish_2, "Molly, Guppy", getString(R.string.dummy)))
        fishItem.add(Pet(R.drawable.fish_3, "Nemo, Goldfish", getString(R.string.dummy)))
        fishItem.add(Pet(R.drawable.fish_4, "Marlin, Goldfish", getString(R.string.dummy)))
        fishItem.add(Pet(R.drawable.fish_5, "Dory, Fish 5", getString(R.string.dummy)))
        val adapter = PetAdapter(fishItem)
        val linLayManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rev.layoutManager = linLayManager
        rev.adapter = adapter

        adapter.setOnClickListener(object : PetAdapter.OnClickListener {
            override fun onClick(position: Int, model: Pet) {
                val bundle = Bundle()
                bundle.putInt("image", model.getImg())
                bundle.putString("name", model.getName())
                bundle.putString("desc", model.getDesc())
                val myIntent = Intent(this@FishScrollActivity, PetDetailsActivity::class.java)
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
//            startActivity(Intent(this, AccountActivity::class.java))
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
                    Toast.makeText(this@FishScrollActivity, getString(R.string.login_fail), Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(result: Void?) {
                    user = User()
                    startActivity(Intent(this@FishScrollActivity, MainActivity::class.java))
                }
            }
        )
    }
}
