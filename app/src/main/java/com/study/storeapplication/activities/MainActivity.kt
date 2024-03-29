package com.study.storeapplication.activities

import SessionData
import adapters.PopularItemsRecyclerAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cartPage.CartActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.study.storeapplication.R
import db.Entities.Product
import db.UserViewModel
import db.UserViewModelFactory
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private var productList: List<Product> = emptyList()

    lateinit var mMap: GoogleMap

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as App).userRepository, (application as App).productRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moveToCartButton = findViewById<LinearLayout>(R.id.cartLayout)
        moveToCartButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        val moveToLoginButton = findViewById<TextView>(R.id.sign_in_out_button)
        moveToLoginButton.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        val signUpButton = findViewById<TextView>(R.id.sign_up_profile_button)
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Создание RecyclerView
        val popularItemsRecyclerView: RecyclerView = findViewById(R.id.popularItemsRecyclerView)
        popularItemsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = PopularItemsRecyclerAdapter(this.productList)
        popularItemsRecyclerView.adapter = adapter
        lifecycleScope.launch{
            userViewModel.getAllProducts().await().observe(
                this@MainActivity, Observer {
                    value -> adapter.setNewList(value)
                }
            )
        }

        // Google-карты
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)




    }

    override fun onStart() {
        super.onStart()
        val moveToLoginButton = findViewById<TextView>(R.id.sign_in_out_button)

        if (SessionData.userData != null){
            moveToLoginButton.text = "logout"
            moveToLoginButton.setOnClickListener {
                SessionData.userData = null
                this@MainActivity.recreate()
            }
        }
        else {
            moveToLoginButton.text = "login"
            moveToLoginButton.setOnClickListener {
                val intent = Intent(this@MainActivity, AuthActivity::class.java)
                startActivity(intent)
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        var point = LatLng(55.99436377197624, 92.79723867546768)
        mMap.addMarker(MarkerOptions().position(point).title("Магазин"))
        mMap.isBuildingsEnabled = true
        mMap.isIndoorEnabled = true
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point))
    }
}