package product_page

import SessionData
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.storeapplication.R
import com.study.storeapplication.activities.AllReviewsActivity
import com.study.storeapplication.activities.App
import com.study.storeapplication.activities.AuthActivity
import db.CartViewModel
import db.CartViewModelFactory
import db.Entities.Cart
import db.Entities.Product
import db.ProductViewModel
import db.ProductViewModelFactory
import kotlinx.coroutines.launch
import utils

class ProductActivity : AppCompatActivity() {

    private lateinit var product: Product

    private val productViewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((application as App).productRepository)
    }

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory((application as App).cartRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        val moveToReviewsButton = findViewById<TextView>(R.id.all_reviews_link)
        moveToReviewsButton.setOnClickListener{
            val intent = Intent(this, AllReviewsActivity:: class.java)
            startActivity(intent)
        }

        val moveBackButton = findViewById<LinearLayout>(R.id.go_back_layout)
        moveBackButton.setOnClickListener{
            this.finish()
        }

        val addProductButton = findViewById<Button>(R.id.product_page_add_product_button)
        addProductButton.setOnClickListener {
            addProductToCart()
        }

        setPageData()
    }

    private fun setPageData(){
        val productId: Long = intent.getLongExtra("productId", -1)
        lifecycleScope.launch{
            productViewModel.getProductById(productId).await().observe(
                this@ProductActivity
            ) { product ->
                this@ProductActivity.product = product
                val nameField = findViewById<TextView>(R.id.product_name)
                nameField.text = product.name
                val priceField = findViewById<TextView>(R.id.product_page_price)
                priceField.text = product.price.toString()

                val images = utils.parseImageUrls(product.images)

                val productImagesRecyclerView: RecyclerView =
                    findViewById<RecyclerView>(R.id.product_page_image_recycler_view)
                productImagesRecyclerView.layoutManager =
                    LinearLayoutManager(this@ProductActivity, LinearLayoutManager.HORIZONTAL, false)
                val adapter = ProductPageImagesRecyclerAdapter(images)
                adapter.setNewList(images)
                productImagesRecyclerView.adapter = adapter

            }
        }
    }

    private fun addProductToCart(){
        val user = SessionData.userData
        if (user != null){
            val newCart = Cart(user.id, this@ProductActivity.product.id, 1)

            println(newCart)

            lifecycleScope.launch {
                cartViewModel.insert(Cart(user.id, this@ProductActivity.product.id, 1))
                cartViewModel.getUserProductsAndAmount(user.id).await().observe(this@ProductActivity){
                    value ->
                    println(value)
                }
            }

        }
        else{
            startActivity(Intent(this@ProductActivity, AuthActivity::class.java))
        }



    }

}