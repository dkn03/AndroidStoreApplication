package product_page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.study.storeapplication.R

public class ProductPageImagesRecyclerAdapter(var images: List<String>):

    RecyclerView.Adapter<ProductPageImagesRecyclerAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.popular_item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_page_image, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val image = images[position]
        Picasso.with(holder.itemView.context)
            .load(image)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.delete_icon)
            .into(holder.image)
    }

    fun setNewList(images: List<String>){
        this.images = images
        notifyDataSetChanged()
    }
}