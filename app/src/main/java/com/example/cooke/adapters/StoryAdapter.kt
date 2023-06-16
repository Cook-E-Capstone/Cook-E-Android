package com.example.cooke.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cooke.ListStoryItem
import com.example.cooke.R
import com.example.cooke.activities.DetailActivity
import com.example.cooke.utils.getElapsedTimeSinceDate

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class StoryAdapter(private val listStory : List<ListStoryItem>) : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(
        R.layout.item_row_post, viewGroup, false))

    override fun getItemCount() = listStory.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (photoUrl, createdAt, name, description, id, lat, lon) = listStory[position]
        holder.tvAuthorItem.text = name
        holder.tvDateItem.text = getElapsedTimeSinceDate(createdAt)
        Glide.with(holder.itemView.context)
            .load(photoUrl)
            .centerCrop()
            .into(holder.ivItem)

        holder.ivItem.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("extra_story", listStory[holder.adapterPosition])

            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(holder.ivItem,"image"),
                )

            holder.itemView.context.startActivity(intentDetail, optionsCompat.toBundle())
        }

    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val ivItem : ImageView = view.findViewById(R.id.img_item_photo)
        val tvAuthorItem : TextView = view.findViewById(R.id.tv_item_author)
        val tvDateItem : TextView = view.findViewById(R.id.tv_item_date)
    }




}