package com.example.itoken.features.assetlibrary.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itoken.databinding.FragmentCollectionInfoBinding
import com.example.itoken.features.assetlibrary.domain.model.InfoCollection
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CollectionInfoFragment : BottomSheetDialogFragment() {

    private var binding: FragmentCollectionInfoBinding? = null
    private lateinit var currentCollection: InfoCollection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCollectionInfoBinding.inflate(layoutInflater)
        currentCollection = arguments?.get("collection") as InfoCollection
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.collectionInfoView?.init(currentCollection)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}