package com.example.cooke.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cooke.R
import com.example.cooke.activities.DetailRecipeActivity
import com.example.cooke.network.responses.ResultsItem

class ListRecipeAdapter(private val listRecipe : List<ResultsItem>) : RecyclerView.Adapter<ListRecipeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(
        R.layout.item_row_recipe, viewGroup, false))

    override fun getItemCount() = listRecipe.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (langkah, seratG, kolesterolMg, bahan, kaloriKkal, karbohidratG, proteinG, gambarUrl, hasil, menu, lemakG) = listRecipe[position]

        val description = "Kalori : $kaloriKkal Kkal\nLemak : $lemakG G\nKarbohidrat : $karbohidratG G\nProtein : $proteinG G\nSerat : $seratG G\nKolesterol : $kolesterolMg Mg"

        holder.tvTitleItem.text = menu
        holder.tvDescItem.text = description
        Glide.with(holder.itemView.context)
            .load(gambarUrl)
            .centerCrop()
            .into(holder.ivItem)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailRecipeActivity::class.java)
            intentDetail.putExtra("extra_recipe", listRecipe[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)

        }

    //        Log.d("MODAN", "onBindViewHolder: $pathfile")
//        holder.tvAuthorItem.text = title
//        holder.tvDateItem.text = content
//        Glide.with(holder.itemView.context)
//            .load(pathfile)
//            .centerCrop()
//            .into(holder.ivItem)

//        holder.ivItem.setOnClickListener {
//            val intentDetail = Intent(holder.itemView.context, DetailPostActivity::class.java)
//            intentDetail.putExtra("extra_story_id", id)

//            val optionsCompat: ActivityOptionsCompat =
//                ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    holder.itemView.context as Activity,
//                    Pair(holder.ivItem,"image"),
//                )

//            holder.itemView.context.startActivity(intentDetail, optionsCompat.toBundle())
//            holder.itemView.context.startActivity(intentDetail)

//        }

    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val ivItem : ImageView = view.findViewById(R.id.img_recipe_photo)
        val tvTitleItem : TextView = view.findViewById(R.id.tv_item_title)
        val tvDescItem : TextView = view.findViewById(R.id.tv_item_desc)
    }




}