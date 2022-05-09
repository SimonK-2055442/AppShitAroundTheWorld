package com.example.appshitaroundtheworld.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appshitaroundtheworld.TodoAdapter
import com.example.appshitaroundtheworld.databinding.FragmentVorigeBestemmingBinding
import com.example.appshitaroundtheworld.model.Land
import com.example.appshitaroundtheworld.model.Persoon
import com.example.appshitaroundtheworld.R


class VorigeBestemmingFragment(val ingelogdePersoon: Persoon) : Fragment(R.layout.fragment_vorige_bestemming) {

    private lateinit var binding: FragmentVorigeBestemmingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVorigeBestemmingBinding.inflate(layoutInflater)
        var landenLijst : List<Land> = ingelogdePersoon.landenLijst
        var adapter = TodoAdapter(landenLijst)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        return binding.root
    }

}