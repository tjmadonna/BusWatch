package com.madonnaapps.buswatch.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.madonnaapps.buswatch.R
import com.madonnaapps.buswatch.domain.model.Stop
import kotlinx.android.synthetic.main.list_item_favorites.view.*

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Stop>() {

        override fun areItemsTheSame(oldItem: Stop, newItem: Stop): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Stop, newItem: Stop): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.routes == newItem.routes
        }
    })

    fun submitStops(stops: List<Stop>, completeCallback: (() -> Unit)?) {
        differ.submitList(stops, completeCallback)
    }

    // RecipeListAdapter methods

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_favorites, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    // View Holder

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Stop) = with(itemView) {
            text_favorites_item_icon.text = item.title[0].toUpperCase().toString()
            text_favorites_item_title.text = item.title
            text_favorites_item_routes.text =
                item.routes.joinToString(separator = ", ")
        }
    }
}