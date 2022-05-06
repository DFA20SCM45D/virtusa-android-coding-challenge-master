package com.virtusa.android.challenges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.virtusa.android.challenges.network.DeliveryItem
import java.util.ArrayList

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    //private val items = ArrayList<ItemRow>()
    private val items = ArrayList<DeliveryItem>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val orders:TextView = itemView.findViewById(R.id.orders)
        private val id:TextView = itemView.findViewById(R.id.id)
        private val count:TextView = itemView.findViewById(R.id.count)
        private val photo: ImageView = itemView.findViewById(R.id.image)

       // fun bind(row: ItemRow)
        //binding views to data for delivery items
        fun bind(row: DeliveryItem){

           var url: String? = null

           orders.text = row.name
           id.text = row.id.toString()
           count.text = row.count.toString()
           url = row.imageUrl

           //using Picasso to load the image
           var picasso : Picasso = Picasso.get()
           if(url != null)
           {  picasso.load(url).error(R.drawable.brokenimage).placeholder(R.drawable.loading).into(photo) }
           else {
               picasso.load(R.drawable.noimage).error(R.drawable.brokenimage).placeholder(R.drawable.loading).into(photo)
           }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(newItems: List<DeliveryItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
