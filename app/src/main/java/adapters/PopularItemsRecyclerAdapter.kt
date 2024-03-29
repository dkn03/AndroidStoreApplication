package adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.study.storeapplication.R
import db.Entities.Product
import product_page.ProductActivity
import utils

class PopularItemsRecyclerAdapter(var products: List<Product>):

    RecyclerView.Adapter<PopularItemsRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nameTextView: TextView = itemView.findViewById(R.id.popular_item_name)
        val priceTextView: TextView = itemView.findViewById(R.id.popular_item_price)
        val imageView: ImageView = itemView.findViewById(R.id.popular_item_image)


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.popular_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = products[position]
        holder.nameTextView.text = product.name
        holder.priceTextView.text = product.price.toString()
        val image = utils.parseImageUrls(product.images)[0]
        Picasso.with(holder.itemView.context)
            .load(image)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.delete_icon)
            .into(holder.imageView)

        holder.itemView.setOnClickListener({
            val intent: Intent = Intent(holder.itemView.context, ProductActivity::class.java)

            intent.putExtra("productId", product.id)
            holder.itemView.context.startActivity(intent)
        })

    }



    fun setNewList(products: List<Product>){
        this.products = products
        notifyDataSetChanged()
    }



}