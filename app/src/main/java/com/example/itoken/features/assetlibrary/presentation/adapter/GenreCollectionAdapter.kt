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
        Pair("Art", R.drawable.art),
        Pair("Music", R.drawable.music),
        Pair("Domain names", R.drawable.domain_names),
        Pair("Utility", R.drawable.utility),
        Pair("Virtual Worlds", R.drawable.virtual_worlds),
        Pair("Trading Cards", R.drawable.trading_cards),
        Pair("Collectibles", R.drawable.collectibies),
        Pair("Sports", R.drawable.sports),
    )

    inner class GenreCollectionViewHolder(
        private val binding: ViewGenreCardviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pair<String, Int>) = with(binding) {
            tvCollectionName.text = item.first
            ivGenre.load(item.second)
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
