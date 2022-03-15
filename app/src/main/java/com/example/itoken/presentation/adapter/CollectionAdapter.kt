package com.example.itoken.presentation.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.R
import com.example.itoken.databinding.ViewTrendingCollectionCardviewBinding
import com.example.itoken.data.entity.Asset

class CollectionAdapter(private val context: Context, private var collectionList: List<Asset>?) : RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    var onClick: ((Asset?) -> (Unit))? = null
    var onLastCardClick: (() -> (Unit))? = null

    inner class CollectionViewHolder(
        private val binding: ViewTrendingCollectionCardviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Asset?, isTheLast: Boolean) {
            with(binding) {
                if (!isTheLast) {
//                    ivCollection.load(Uri.parse(item.collection?.image_url))
//                    ivCreator.load(Uri.parse(item.creator?.profile_img_url))
//                    tvCollectionName.text = item.collection?.name
//                    tvCreatorName.text = item.creator?.user?.username
                } else {
//                    with(collectionCardview) {
//                        setBackgroundResource(
//                            R.drawable.gradient_login)
//                        setOnClickListener {
//                            onLastCardClick?.invoke()
//                        }
//                    }
//                    tvCreatorName.text = context.getString(R.string.see_collections_notif)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder =
        CollectionViewHolder(
            ViewTrendingCollectionCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) =
        holder.bind(collectionList?.get(position), position == 8)

    override fun getItemCount(): Int = 0//if (collectionList.size > 9) 9 else collectionList.size

}