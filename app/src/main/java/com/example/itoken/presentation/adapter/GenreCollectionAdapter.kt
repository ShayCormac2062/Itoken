package com.example.itoken.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.R
import com.example.itoken.databinding.ViewGenreCardviewBinding

class GenreCollectionAdapter : RecyclerView.Adapter<GenreCollectionAdapter.GenreCollectionViewHolder>() {

    var onClick: ((String) -> (Unit))? = null
    private val genreNameList: List<Pair<String, Int>> = listOf(
        Pair("Art", R.drawable.ai),
        Pair("Music", R.drawable.ai),
        Pair("Domain names", R.drawable.ai),
        Pair("Utility", R.drawable.ai),
        Pair("Virtual Worlds", R.drawable.ai),
        Pair("Trading Cards", R.drawable.ai),
        Pair("Collectibles", R.drawable.ai),
        Pair("Sports", R.drawable.ai),
    )

    inner class GenreCollectionViewHolder(
        private val binding: ViewGenreCardviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pair<String, Int>) = with(binding) {
            tvCollectionName.text = item.first
            ivGenre.setImageResource(item.second)
            collectionCardview.setOnClickListener {
                it.alpha = 0.5f
                onClick?.invoke(item.first)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreCollectionViewHolder =
        GenreCollectionViewHolder(
            ViewGenreCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: GenreCollectionViewHolder, position: Int) =
        holder.bind(genreNameList[position])

    override fun getItemCount(): Int = genreNameList.size

}