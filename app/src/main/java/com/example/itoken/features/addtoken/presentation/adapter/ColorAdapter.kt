package com.example.itoken.features.addtoken.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.databinding.ViewColorBinding
import coil.load
import com.example.itoken.R

class ColorAdapter : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    var onClick: ((Int) -> (Unit))? = null
    private val colorList: List<Int> = listOf(
        R.drawable.red,
        R.drawable.yellow,
        R.drawable.green,
        R.drawable.blue,
        R.drawable.white,
        R.drawable.cyan,
        R.drawable.black
    )

    inner class ColorViewHolder(
        private val binding: ViewColorBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) = with(binding) {
            ivColor.load(item)
            btnColor.setOnClickListener {
                onClick?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder =
        ColorViewHolder(
            ViewColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) =
        holder.bind(colorList[position])

    override fun getItemCount(): Int = colorList.size

}