package cartPage

import SessionData
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.storeapplication.R
import com.study.storeapplication.activities.App
import com.study.storeapplication.activities.PaymentActivity
import db.CartViewModel
import db.CartViewModelFactory
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity() {

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory((application as App).cartRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val moveBackButton = findViewById<LinearLayout>(R.id.go_back_layout)
        moveBackButton.setOnClickListener{
            this.finish()
        }

        val moveToPaymentButton = findViewById<Button>(R.id.payment_activity_pay_button)
        moveToPaymentButton.setOnClickListener{
            val intent = Intent(this, PaymentActivity:: class.java)
            startActivity(intent)
        }

        val cartItemsRecyclerView: RecyclerView = findViewById(R.id.cart_items_recyclerView)
        cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        val user = SessionData.userData
        if (user != null) {
            val cartItemData = cartViewModel.getUserProductsAndAmount(user.id)
            lifecycleScope.launch{
                cartViewModel.getUserProductsAndAmount(user.id).await().observe(this@CartActivity){
                    value ->
                    cartItemsRecyclerView.adapter = CartItemsRecyclerAdapter(value, cartViewModel, lifecycleScope)
                    println(value)
                }
            }

        }
        //
    }
}