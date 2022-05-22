package com.example.itoken.features.addtoken.presentation.canvas

import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.R
import com.example.itoken.databinding.FragmentCanvasBinding
import com.example.itoken.features.addtoken.presentation.adapter.ColorAdapter
import com.example.itoken.utils.CommonUtils

class Linen(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context) {

    private val binding by lazy { FragmentCanvasBinding.bind(this) }

    fun init(contentResolver: ContentResolver?, dialog: AlertDialog) {
        binding.run {
            paintingZone.init()
            btnLittle.setOnClickListener {
                paintingZone.sizeSmall()
            }
            btnMedium.setOnClickListener {
                paintingZone.sizeNormal()
            }
            btnBig.setOnClickListener {
                paintingZone.sizeBig()
            }
            btnSave.setOnClickListener {
                CommonUtils.makeToast(context, context.getString(R.string.paint_saved_confirm))
                paintingZone.buildDrawingCache()
                loadPaint(contentResolver, dialog)
            }
            rvColors.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = RecyclerView.HORIZONTAL
                }
                adapter = ColorAdapter().apply {
                    onClick = { color ->
                        with(paintingZone) {
                            when (color) {
                                R.drawable.red -> colorRed()
                                R.drawable.yellow -> colorYellow()
                                R.drawable.green -> colorGreen()
                                R.drawable.blue -> colorBlue()
                                R.drawable.white -> colorWhite()
                                R.drawable.cyan -> colorCyan()
                                else -> colorBlack()
                            }
                        }
                    }
                    setItemViewCacheSize(8)
                }
            }
        }
    }

    private fun loadPaint(contentResolver: ContentResolver?, dialog: AlertDialog) {
        val bitmap = binding.paintingZone.drawingCache
        MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            "${System.currentTimeMillis()}token.jpg",
            "beautiful"
        )
        dialog.dismiss()
    }

}