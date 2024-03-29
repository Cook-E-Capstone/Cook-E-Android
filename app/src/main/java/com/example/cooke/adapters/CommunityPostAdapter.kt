package com.example.cooke.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cooke.R
import com.example.cooke.activities.DetailPostActivity
import com.example.cooke.network.responses.CommunityItem


class CommunityPostAdapter(private val listPost : List<CommunityItem>) : RecyclerView.Adapter<CommunityPostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(viewGroup.context).inflate(
        R.layout.item_row_post, viewGroup, false))

    override fun getItemCount() = listPost.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (createdAt, pathfile, id, title, userID, content, updatedAt) = listPost[position]
        Log.d("MODAN", "onBindViewHolder: $pathfile")
        val postTitle = if (title?.length!! > 18) "${title.take(18)}..." else title
        val postDesc = if (content?.length!! > 160) "${content.take(160)}..." else content
        holder.tvAuthorItem.text = postTitle
        holder.tvDateItem.text = postDesc
        Glide.with(holder.itemView.context)
            .load(pathfile)
            .centerCrop()
            .into(holder.ivItem)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailPostActivity::class.java)
            intentDetail.putExtra("extra_story_id", id)

//            val optionsCompat: ActivityOptionsCompat =
//                ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    holder.itemView.context as Activity,
//                    Pair(holder.ivItem,"image"),
//                )

//            holder.itemView.context.startActivity(intentDetail, optionsCompat.toBundle())
            holder.itemView.context.startActivity(intentDetail)

        }

    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val ivItem : ImageView = view.findViewById(R.id.img_item_photo)
        val tvAuthorItem : TextView = view.findViewById(R.id.tv_item_author)
        val tvDateItem : TextView = view.findViewById(R.id.tv_item_date)
    }




}