package com.example.itoken.features.assetlibrary.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.R
import com.example.itoken.databinding.ViewGenreCardviewBinding

class GenreCollectionAdapter : RecyclerView.Adapter<GenreCollectionAdapter.GenreCollectionViewHolder>() {

    var onClick: ((String) -> (Unit))? = null
    private val genreNameList: List<Pair<String, Int>> = listOf(
        Pair("Art", R.drawable.gradient_login),
        Pair("Music", R.drawable.gradient_music),
        Pair("Domain names", R.drawable.gradient_domain_names),
        Pair("Utility", R.drawable.gradient_utility),
        Pair("Virtual Worlds", R.drawable.gradient_virtual_worlds),
        Pair("Trading Cards", R.drawable.gradient_trading_cards),
        Pair("Collectibles", R.drawable.gradient_collectibles),
        Pair("Sports", R.drawable.gradient_sports),
    )

    inner class GenreCollectionViewHolder(
        private val binding: ViewGenreCardviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pair<String, Int>) = with(binding) {
            tvCollectionName.text = item.first
            ivGenre.load(item.second)
            collectionCardview.setOnClickListener {
                onClick?.invoke(item.first)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreCollectionViewHolder =
        GenreCollectionViewHolder(
            ViewGenreCardviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )

    override fun onBindViewHolder(holder: GenreCollectionViewHolder, position: Int) =
        holder.bind(genreNameList[position])

    override fun getItemCount(): Int = genreNameList.size

}
