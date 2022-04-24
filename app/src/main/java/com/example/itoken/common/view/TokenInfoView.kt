package com.example.itoken.common.view

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import coil.load
import com.example.itoken.R
import com.example.itoken.common.entity.BaseAsset
import com.example.itoken.common.fragment.TokenInfoFragment
import com.example.itoken.databinding.FragmentTokenInfoBinding
import com.example.itoken.databinding.ViewNotificationBinding

class TokenInfoView<M : BaseAsset>(
    context: Context,
    attrs: AttributeSet? = null
) : NestedScrollView(context) {

    private val binding by lazy { FragmentTokenInfoBinding.bind(this) }
    private lateinit var currentAsset: M

    fun init(
        asset: M,
        likes: Long,
        isUserAuthorized: Boolean,
        isUserBoughtThisAsset: Boolean
    ) {
        currentAsset = asset
        binding.run {
            imageView.load(Uri.parse(asset.imagePreviewUrl))
            ivToken.load(Uri.parse(asset.imageUrl))
            tvCreatorName.text = asset.creatorName
            tvTokenName.text = asset.tokenName
            tvPrice.text = "Цена: ${asset.price} ICrystal"
            tvFavourite.text = likes.toString()
            tvDescription.text = asset.description
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
                            showDialog(
                                "Вы уверены, что хотите продать токен на аукционе? Продолжить?",
                                "Ваш токен отправлен системе продаж",
                                true
                            )
                        }
                    }
                }
            } else if (isUserAuthorized) {
                with(btnToCollected) {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        if (TokenInfoFragment.isUserHasEnoughMoney) {
                            showDialog(
                                "Вы уверены, что хотите купить токен? С вас будет списано " +
                                    "${currentAsset.price} ICrystal. Продолжить?",
                                "Токен был успешно добавлен в библиотеку",
                                false
                            )
                        } else makeToast("Недостаточно средств на покупку токена!")
                    }
                }
            }
        }
    }

    private fun makeToast(s: String) =
        Toast.makeText(
            context,
            s,
            Toast.LENGTH_SHORT
        ).show()

    private fun showDialog(message: String, notifForToast: String, isForTrade: Boolean) {
        val bindingOfDialog: ViewNotificationBinding
        val alerts = AlertDialog.Builder(context).apply {
            bindingOfDialog = ViewNotificationBinding.inflate(LayoutInflater.from(context))
            setView(bindingOfDialog.root)
        }.show()
        with(bindingOfDialog) {
            btnNo.setOnClickListener {
                alerts.dismiss()
            }
            btnYes.setOnClickListener {
                if (!isForTrade) TokenInfoFragment.isNeedToCollect = true
                else TokenInfoFragment.isNeedToTrade = true
                makeToast(notifForToast)
                alerts.dismiss()
            }
            tvNotification.text = message
        }
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
}