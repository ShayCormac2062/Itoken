package com.example.itoken.features.assetlibrary.presentation.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView
import coil.load
import com.example.itoken.R
import com.example.itoken.databinding.FragmentCollectionInfoBinding
import com.example.itoken.features.assetlibrary.domain.model.InfoCollection

class CollectionInfoView(
    context: Context,
    attrs: AttributeSet? = null
) : NestedScrollView(context) {

    private val binding by lazy { FragmentCollectionInfoBinding.bind(this) }

    fun init(collection: InfoCollection) {
        with(binding) {
            imageView.load(collection.bannerImageUrl)
            ivCollection.load(collection.imageUrl)
            tvCreatorName.text = collection.username
            tvCollectionName.text = collection.name
            tvDescription.text = collection.description
            tvPrice.text = String.format(context.getString(R.string.price_amount_for_collection), collection.averagePrice ?: "0")
            tvDateValue.text = collection.createdDate?.substring(0, 10)
            tvCountValue.text = collection.count?.toString()
        }
    }
}