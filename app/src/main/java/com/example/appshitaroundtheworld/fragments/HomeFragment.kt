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
    var weetjesLijst: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.aantalLanden.setText("Je hebt " + ingelogdePersoon.aantalLandenBezocht().toString() + " landen bezocht")
        binding.besteLand.setText("Jouw beste ervaring was in "+ ingelogdePersoon.besteLand.naam)
        binding.slechtsteLand.setText("Jouw slechtste ervaring was in " + ingelogdePersoon.slechtsteLand.naam)
        weetjes()
        var weetjeVanDeDag = weetjesLijst.get((0..(weetjesLijst.size-1)).random())
        binding.Weetjes.setText("Wist je dat???\n" + weetjeVanDeDag)
        return binding.root
    }

    private fun weetjes(){
        weetjesLijst.add("De grootste drol ooit was bijna 8m lang")
        weetjesLijst.add("Elke dag meer dan 2.000 kinderen sterven aan diaree")
        weetjesLijst.add("We per week gemiddeld 3h en 9min per week opt wc zitten")
        weetjesLijst.add("Dat poep meer bacterieën bevat dan oud voedsel")
        weetjesLijst.add("Dat er kak implantaten gedaan worden")
        weetjesLijst.add("The appolo passagiers niet alleen voetafdrukken achterlieten op de maan maar ook 96 zakken met menselijke uitlaten")
        weetjesLijst.add("Pandas gaan 40 keer per week naar het WC")
        weetjesLijst.add("De dinodrol van Sint-Martinus nog steeds niet opgeist is door zijn recordhouder")
        weetjesLijst.add("Wombats kubusvormige drollen leggen, ookal is hun anus rond")
        weetjesLijst.add("Luiaards slecht 1 keer week kaken, dit noemt dan de kakdans")
    }
}