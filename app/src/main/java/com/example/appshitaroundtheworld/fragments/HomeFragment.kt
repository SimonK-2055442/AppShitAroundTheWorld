package com.example.appshitaroundtheworld.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.appshitaroundtheworld.databinding.FragmentHomeBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appshitaroundtheworld.model.Persoon
import com.example.appshitaroundtheworld.R

class HomeFragment(val ingelogdePersoon: Persoon) : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.aantalLanden.setText("Je hebt " + ingelogdePersoon.aantalLandenBezocht().toString() + " landen bezocht")
        binding.besteLand.setText("Jouw beste ervaring was in "+ ingelogdePersoon.besteLand.naam)
        binding.slechtsteLand.setText("Jouw slechtste ervaring was in " + ingelogdePersoon.slechtsteLand.naam)
        return binding.root
    }


}