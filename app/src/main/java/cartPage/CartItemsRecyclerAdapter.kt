package cartPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.study.storeapplication.R
import db.CartViewModel
import db.Entities.Cart
import db.Entities.CartProductAndAmount
import kotlinx.coroutines.launch
import utils

class CartItemsRecyclerAdapter(var products: List<CartProductAndAmount>, val cartViewModel: CartViewModel, val scope: LifecycleCoroutineScope):

    RecyclerView.Adapter<CartItemsRecyclerAdapter.CartViewHolder>() {

        var productsArrayList: ArrayList<CartProductAndAmount> = products as ArrayList<CartProductAndAmount>

        inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.findViewById(R.id.cart_item_name)
            val priceTextView: TextView = itemView.findViewById(R.id.cart_item_price)
            val imageView: ImageView = itemView.findViewById(R.id.cart_item_image)
            val deleteButton: ImageButton = itemView.findViewById(R.id.cart_item_delete_button)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return productsArrayList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = productsArrayList[position]
        holder.nameTextView.text = item.name
        holder.priceTextView.text = item.price.toString()
        val imageUrl = utils.parseImageUrls(item.images)[0]

        Picasso.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.delete_icon)
            .into(holder.imageView)

        holder.deleteButton.setOnClickListener{
            productsArrayList.removeAt(position)
            scope.launch {
                cartViewModel.deleteCart(Cart(item.userId, item.productId, item.amount))
            }
            notifyItemRemoved(position)
        }
    }
}
