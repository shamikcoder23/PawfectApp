package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
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

class DogScrollActivity : AppCompatActivity() {
    private lateinit var dogItem : ArrayList<Pet>

    private lateinit var account: Auth0

    private var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dog_scroll)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        account = Auth0( getString(R.string.auth0_clientId), getString(R.string.auth0_domain) )

        val rev : RecyclerView = findViewById(R.id.rev)
        dogItem = ArrayList()
        dogItem.add(Pet(R.drawable.dog_1, "Lisa, Labrador", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_2, "Don, Labrador", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_3, "Remy, Husky", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_4, "Butch, Labrador", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_5, "Maple, Rottweiler", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_6, "Hammy, Rottweiler", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_7, "Dona, Husky", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_8, "Olivia, Husky", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_9, "Jim and Jam, Labradors", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_10, "Powder, Husky", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_11, "Connor, Husky", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_12, "Ginger, Husky", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_13, "Kim, Husky", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_14, "Sunny, Golden Retriever", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_15, "Rocky, Rottweiler", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_16, "Luna, Labrador", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_17, "Pluto, Corgi", getString(R.string.dummy)))
        dogItem.add(Pet(R.drawable.dog_18, "Alice, Labrador", getString(R.string.dummy)))
        val adapter = PetAdapter(dogItem)
        val linLayManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rev.layoutManager = linLayManager
        rev.adapter = adapter

        adapter.setOnClickListener(object : PetAdapter.OnClickListener {
            override fun onClick(position: Int, model: Pet) {
                val bundle = Bundle()
                bundle.putInt("image", model.getImg())
                bundle.putString("name", model.getName())
                bundle.putString("desc", model.getDesc())
                val myIntent = Intent(this@DogScrollActivity, PetDetailsActivity::class.java)
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
            //startActivity(Intent(this, AccountActivity::class.java))
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
                    Toast.makeText(this@DogScrollActivity, getString(R.string.login_fail), Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(result: Void?) {
                    user = User()
                    startActivity(Intent(this@DogScrollActivity, MainActivity::class.java))
                }
            }
        )
    }
}
