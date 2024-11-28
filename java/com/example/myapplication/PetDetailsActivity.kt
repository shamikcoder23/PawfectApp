package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider

class PetDetailsActivity : AppCompatActivity() {

    private lateinit var account: Auth0

    private var user = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pet_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        account = Auth0( getString(R.string.auth0_clientId), getString(R.string.auth0_domain) )

        val bundle = intent.extras
        val img = bundle?.getInt("image")
        val name = bundle?.getString("name")
        val desc = bundle?.getString("desc")

        val image: ImageView = findViewById(R.id.list_item)
        if (img != null) {
//            val bm: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground)
//            image.setImageBitmap(Bitmap.createScaledBitmap(bm, 100, 100, false))
            image.setImageResource(img)
        }
        var txt: TextView = findViewById(R.id.list_item_name)
        txt.text = name
        txt = findViewById(R.id.list_item_desc)
        txt.text = desc

        val chatBtn : Button = findViewById(R.id.chat)
        chatBtn.setOnClickListener{
            Toast.makeText(this, R.string.none, Toast.LENGTH_LONG).show()
            //startActivity(Intent(this, AccountActivity::class.java))
        }
        val addBtn : Button = findViewById(R.id.add)
        addBtn.setOnClickListener{
            Toast.makeText(this, R.string.none, Toast.LENGTH_LONG).show()
            //startActivity(Intent(this, AccountActivity::class.java))
        }

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
                    Toast.makeText(this@PetDetailsActivity, getString(R.string.login_fail), Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(result: Void?) {
                    user = User()
                    startActivity(Intent(this@PetDetailsActivity, MainActivity::class.java))
                }
            }
        )
    }
}