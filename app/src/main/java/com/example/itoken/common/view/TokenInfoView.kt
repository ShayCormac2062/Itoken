package com.example.itoken.common.view

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import androidx.core.widget.NestedScrollView
import coil.ImageLoader
import coil.load
import com.example.itoken.R
import com.example.itoken.common.entity.BaseAsset
import com.example.itoken.common.fragment.TokenInfoFragment
import com.example.itoken.databinding.FragmentTokenInfoBinding
import com.example.itoken.utils.CommonUtils
import java.io.File
import java.io.FileOutputStream
import java.util.*


class TokenInfoView<M : BaseAsset>(
    context: Context,
    attrs: AttributeSet? = null
) : NestedScrollView(context) {

    private val binding by lazy { FragmentTokenInfoBinding.bind(this) }
    private lateinit var currentAsset: M

    fun init(
        asset: M,
        likes: Int,
        isUserAuthorized: Boolean,
        isUserBoughtThisAsset: Boolean,
        contentResolver: ContentResolver? = null
    ) {
        currentAsset = asset
        val imageLoader = ImageLoader.Builder(context).allowHardware(false).build()
        binding.run {
            imageView.load(Uri.parse(asset.imagePreviewUrl))
            ivToken.load(Uri.parse(asset.imageUrl), imageLoader)
            tvCreatorName.text = if (!asset.creatorName.isNullOrEmpty()) asset.creatorName else "Автор не известен"
            tvTokenName.text = if (!asset.tokenName.isNullOrEmpty()) asset.tokenName else "Название не указано"
            tvPrice.text = String.format(context.getString(R.string.price_amount), asset.price)
            tvFavourite.text = String.format(context.getString(R.string.favorites_amount), likes)
            tvCollected.text = String.format(context.getString(R.string.owners_amount), countOwners())
            tvCreated.text = String.format(context.getString(R.string.creators_amount), if (!asset.creatorName.isNullOrEmpty()) 1 else 0)
            tvDescription.text = String.format(context.getString(R.string.description_value), asset.description, if (isUserAuthorized || isUserBoughtThisAsset) "" else "\n")
            tvContractAddressValue.text = asset.address
            tvTokenIdValue.text = asset.tokenId
            tvTokenStandardsValue.text = (100..365).random().toString()
            tvBlockchainValue.text = asset.ownerName
            tvMetadataValue.text = "По умолчанию"
            if (isUserBoughtThisAsset) {
                with(cardviewShowDetails) {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        onDetailClick(cardviewDetails.visibility == View.VISIBLE)
                    }
                }
                if (TokenInfoFragment.isUserCreator) {
                    with(btnCreateTrade) {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            CommonUtils.showDialog(
                                context,
                                context.getString(R.string.send_token),
                                onClickEvent = {
                                    TokenInfoFragment.isNeedToTrade = true
                                    CommonUtils.makeToast(context, context?.getString(R.string.send_token_confirm))
                                }
                            )
                        }
                    }
                }
                with(ivDownload) {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        CommonUtils.showDialog(
                            context,
                            context.getString(R.string.token_download_confirm),
                            onClickEvent = {
                                loadPaint(contentResolver)
                                CommonUtils.makeToast(context, context.getString(R.string.token_downloaded))
                            }
                        )

                    }
                }
            } else if (isUserAuthorized) {
                with(btnToCollected) {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        if (TokenInfoFragment.isUserHasEnoughMoney) {
                            CommonUtils.showDialog(
                                context,
                                String.format(context.getString(R.string.buy_token), asset.price),
                                onClickEvent = {
                                    TokenInfoFragment.isNeedToCollect = true
                                    CommonUtils.makeToast(context, context?.getString(R.string.buy_token_confirm))
                                }
                            )
                        } else CommonUtils.makeToast(context, context.getString(R.string.buy_token_deny))
                    }
                }
            }
        }
    }

    private fun countOwners(): Int {
        return if (
            currentAsset.creatorName.toString().isNotEmpty() &&
            (currentAsset.ownerName.toString().isNotEmpty() &&
                    currentAsset.ownerName.toString() != "NullAddress")
        ) 2
        else if (
            currentAsset.creatorName.toString().isNotEmpty() ||
            (currentAsset.ownerName.toString().isNotEmpty() &&
                    currentAsset.ownerName.toString() != "NullAddress")
        ) 1
        else 0
    }

    private fun onDetailClick(isEnabled: Boolean) {
        binding.cardviewDetails.run {
            if (!isEnabled) visibility = View.VISIBLE
            startAnimation(
                AnimationUtils
                    .loadAnimation(
                        context,
                        if (!isEnabled) R.anim.swipe_down else R.anim.swipe_up
                    )
            )
            if (isEnabled) visibility = View.GONE
            binding.ivCollectedCard.load(if (isEnabled) R.drawable.arrow_down else R.drawable.arrow_up)
        }
    }

    private fun loadPaint(contentResolver: ContentResolver?) {
        val bitmap = binding.ivToken.drawToBitmap().copy(Bitmap.Config.ARGB_8888, false)
        MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            "${System.currentTimeMillis()}token.jpg",
            "beautiful"
        )
    }
}