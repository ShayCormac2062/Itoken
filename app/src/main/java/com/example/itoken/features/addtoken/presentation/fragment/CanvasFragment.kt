package com.example.itoken.features.addtoken.presentation.fragment

import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.R
import com.example.itoken.databinding.FragmentCanvasBinding
import com.example.itoken.features.addtoken.presentation.adapter.ColorAdapter

class CanvasFragment : Fragment() {

    private var binding: FragmentCanvasBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCanvasBinding.inflate(layoutInflater)
        return binding?.root
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
        findNavController().popBackStack()
        parentFragmentManager.beginTransaction()
            .add(AddTokenFragment(), "FUCK")
            .commit()
        binding = null
        super.onDestroyView()
    }
}