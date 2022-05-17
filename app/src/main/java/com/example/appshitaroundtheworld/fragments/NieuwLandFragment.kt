package com.example.appshitaroundtheworld.fragments

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import com.example.appshitaroundtheworld.MainActivity
import com.example.appshitaroundtheworld.MainNavigation
import com.example.appshitaroundtheworld.R
import com.example.appshitaroundtheworld.databinding.FragmentNieuwLandBinding
import com.example.appshitaroundtheworld.model.Geheugen
import com.example.appshitaroundtheworld.model.Land
import com.example.appshitaroundtheworld.model.Persoon
import com.google.android.material.snackbar.Snackbar
import java.util.*


class NieuwLandFragment(val ingelogdePersoon: Persoon) : Fragment(R.layout.fragment_nieuw_land) {

    private lateinit var binding: FragmentNieuwLandBinding
    private lateinit var locationPermissionResult: ActivityResultLauncher<String>
    private lateinit var LocationResult: ActivityResultLauncher<Void>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentNieuwLandBinding.inflate(layoutInflater)
        var datumVandaag = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString() + "/" + (Calendar.getInstance().get(Calendar.MONTH)+1).toString() + "/" + Calendar.getInstance().get(Calendar.YEAR).toString()
        binding.datum.setText(datumVandaag)
        binding.toevoegen.setOnClickListener {
            //geeft fout als land of datum leeg zijn
            if (binding.landNaam.text.toString() == "" || binding.datum.text.toString() == "") {
                Snackbar
                    .make(it, "vul een geldig land en een geldige datum in", Snackbar.LENGTH_LONG)
                    .show()
            }
            else {
                val geocoder = Geocoder(context, Locale.getDefault())
                var naamLand = binding.landNaam.text.toString()
                var comment = binding.comment.text.toString();
                var rating = binding.ratingBar.rating.toDouble()
                var datum = binding.datum.text.toString()
                var datumString: List<String> = datum.split("/")
                var dag: Int = datumString[0].toInt()
                var maand: Int = datumString[1].toInt()
                var jaar: Int = datumString[2].toInt()


                var adresLijst: List<Address> = geocoder.getFromLocationName(naamLand, 1)
                if (adresLijst.size > 0) {
                    var adres: Address = adresLijst.get(0)
                    var latitude: Double = adres.latitude
                    var longitude: Double = adres.longitude
                    var eersteKeer = true
                    ingelogdePersoon.landenLijst.forEach{
                        if(it.naam == naamLand){
                            eersteKeer = false
                        }
                    }
                    var nieuwLand = Land(naamLand, rating, comment, jaar, maand, dag, latitude, longitude, eersteKeer)
                    ingelogdePersoon.voegLandToe(nieuwLand)
                    var aantalNieuweAchievements = achievementCheck(ingelogdePersoon)
                    if(aantalNieuweAchievements > 0 ) {
                        Snackbar
                            .make(
                                it,
                                "Je hebt " + aantalNieuweAchievements + " nieuwe achievements behaald",
                                Snackbar.LENGTH_LONG
                            )
                            .show()
                    }
                    else if(nieuwLand.eersteKeer){
                        Snackbar
                            .make(
                                it,
                                "Je hebt een nieuw land toegevoegd",
                                Snackbar.LENGTH_LONG
                            )
                            .show()
                    }
                    else{
                        Snackbar
                            .make(
                                it,
                                "Je heb dit land al bezocht, was het deze keer leuker?",
                                Snackbar.LENGTH_LONG
                            )
                            .show()
                    }
                }
                else{
                    Snackbar
                        .make(
                            it,
                            "Sorry, land niet gevonden",
                            Snackbar.LENGTH_LONG
                        )
                        .show()
                }
            }
        }

        return binding.root
    }
    private fun achievementCheck(persoon:Persoon): Int {
        var achievements1 = persoon.behaaldeAchievements.size
        persoon.achievementCheck()
        var achievements2 = persoon.behaaldeAchievements.size
        return (achievements2 - achievements1)
    }
}