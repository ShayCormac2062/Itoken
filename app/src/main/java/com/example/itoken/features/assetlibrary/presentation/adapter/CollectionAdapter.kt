package com.example.itoken.features.assetlibrary.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.itoken.R
import com.example.itoken.databinding.ViewTrendingCollectionCardviewBinding
import com.example.itoken.features.assetlibrary.presentation.model.CollectionBrief

class CollectionAdapter(private var collectionList: List<CollectionBrief>) :
    RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    var onClick: ((CollectionBrief?) -> (Unit))? = null

    inner class CollectionViewHolder(
        private val binding: ViewTrendingCollectionCardviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CollectionBrief?) {
            with(binding) {
                ivCollection.load(item?.bannerImageUrl)
                ivCreator.load(item?.bannerImageUrl)
                tvCollectionName.text = item?.name
                tvCreatorName.text = item?.username
                collectionCardview.setOnClickListener {
                    onClick?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder =
        CollectionViewHolder(
            ViewTrendingCollectionCardviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) =
        holder.bind(collectionList[position])

    override fun getItemCount(): Int = collectionList.size

}
