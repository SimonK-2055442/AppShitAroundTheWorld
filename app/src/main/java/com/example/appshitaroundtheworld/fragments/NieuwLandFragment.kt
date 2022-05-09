package com.example.appshitaroundtheworld.fragments

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appshitaroundtheworld.model.Persoon
import com.example.appshitaroundtheworld.R
import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.common.util.CollectionUtils

import com.example.appshitaroundtheworld.databinding.FragmentNieuwLandBinding
import com.example.appshitaroundtheworld.model.Land
import java.util.*

class NieuwLandFragment(val ingelogdePersoon: Persoon) : Fragment(R.layout.fragment_nieuw_land) {

    private lateinit var binding: FragmentNieuwLandBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentNieuwLandBinding.inflate(layoutInflater)
        binding.toevoegen.setOnClickListener{
            //geeft fout als land of datum leeg zijn
            val geocoder = Geocoder(context, Locale.getDefault())
            var naamLand = binding.landNaam.text.toString()
            var comment = binding.comment.text.toString();
            var rating = binding.ratingBar.rating.toDouble()
            var datum = binding.datum.text.toString()
            var datumString : List<String> = datum.split("/")
            var dag : Int = datumString[0].toInt()
            var maand : Int = datumString[1].toInt()
            var jaar : Int = datumString[2].toInt()


            var adresLijst: List<Address> = geocoder.getFromLocationName(naamLand, 1)
            if (adresLijst.size > 0){
                var adres : Address = adresLijst.get(0)
                var latitude : Double = adres.latitude
                var longitude : Double = adres.longitude
                var nieuwLand = Land(naamLand,rating,comment,jaar,maand,dag,latitude, longitude)
                ingelogdePersoon.landenLijst.add(nieuwLand)

            }
        }

        return binding.root
    }


}