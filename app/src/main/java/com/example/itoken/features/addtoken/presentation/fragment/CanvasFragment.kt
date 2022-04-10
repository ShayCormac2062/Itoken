package com.example.itoken.features.addtoken.presentation.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.R
import com.example.itoken.databinding.FragmentCanvasBinding
import com.example.itoken.features.addtoken.presentation.adapter.ColorAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CanvasFragment : BottomSheetDialogFragment() {

    private var binding: FragmentCanvasBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCanvasBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        val d = dialog as BottomSheetDialog
        d.setContentView(R.layout.fragment_canvas)
        try {
            val behaviorField = d.javaClass.getDeclaredField("behavior").apply {
                isAccessible = true
            }
            (behaviorField.get(d) as BottomSheetBehavior<*>).apply {
                binding?.root?.let {
                    peekHeight = it.height
                }
                setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        state = BottomSheetBehavior.STATE_EXPANDED
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}

                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
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
                Toast.makeText(
                    context,
                    "Рисунок был сохранён в галерею. Возьмите его оттуда",
                    Toast.LENGTH_LONG
                ).show()
                paintingZone.buildDrawingCache()
                val bitmap = paintingZone.drawingCache
                MediaStore.Images.Media.insertImage(
                    activity?.contentResolver,
                    bitmap,
                    "${System.currentTimeMillis()}token.jpg",
                    "beautiful"
                )
                onDestroyView()
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}