package com.example.itoken.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itoken.databinding.FragmentSearchBinding
import com.example.itoken.presentation.adapter.GenreCollectionAdapter
import com.example.itoken.presentation.adapter.TokenAdapter
import com.example.itoken.domain.model.InfoAsset

class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
            rvGenres.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = RecyclerView.HORIZONTAL
                }
                adapter = GenreCollectionAdapter()
            }
            rvPopularTokens.apply {
                layoutManager = GridLayoutManager(context, 2).apply {
                    orientation = RecyclerView.VERTICAL
                }
                adapter = TokenAdapter( null, context).apply {
                    onClick = { asset, likes ->
                        //swapTokenInfoBottomSheet(asset, likes)
                    }
                }
            }
        }
    }

    private fun swapTokenInfoBottomSheet(asset: InfoAsset?, likes: Int?) {
        parentFragmentManager.beginTransaction()
            .add(TokenInfoFragment(asset, likes), "SHIT")
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
