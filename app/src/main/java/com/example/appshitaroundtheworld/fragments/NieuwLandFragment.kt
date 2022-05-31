package com.example.appshitaroundtheworld.fragments

import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.fragment.app.Fragment
import com.example.appshitaroundtheworld.MainNavigation
import com.example.appshitaroundtheworld.R
import com.example.appshitaroundtheworld.databinding.FragmentNieuwLandBinding
import com.example.appshitaroundtheworld.model.Land
import com.example.appshitaroundtheworld.model.Persoon
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest


class NieuwLandFragment(val ingelogdePersoon: Persoon, var mainNavigation: MainNavigation) : Fragment(R.layout.fragment_nieuw_land) {

    private lateinit var binding: FragmentNieuwLandBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentNieuwLandBinding.inflate(layoutInflater)
        var datumVandaag = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString() + "/" + (Calendar.getInstance().get(Calendar.MONTH)+1).toString() + "/" + Calendar.getInstance().get(Calendar.YEAR).toString()
        var naamLand:String
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
                var plaatsLand = binding.landNaam.text.toString()
                var comment = binding.comment.text.toString();
                var rating = binding.ratingBar.rating.toDouble()
                var datum = binding.datum.text.toString()
                var datumString: List<String> = datum.split("/")
                datumString[0].replaceFirst("^0+(?!$)","")
                datumString[1].replaceFirst("^0+(?!$)","")
                datumString[2].replaceFirst("^0+(?!$)","")
                if(datumString.size == 3 && !(datumString[0].toIntOrNull() == null) && !(datumString[1].toIntOrNull() == null) && !(datumString[2].toIntOrNull() == null)) {
                    var dag: Int = datumString[0].toInt()
                    var maand: Int = datumString[1].toInt()
                    var jaar: Int = datumString[2].toInt()


                var adresLijst: List<Address> = geocoder.getFromLocationName(plaatsLand, 1)
                if (adresLijst.size > 0) {
                    var adres: Address = adresLijst.get(0)
                    var latitude: Double = adres.latitude
                    var longitude: Double = adres.longitude
                    if (!(getLandNaam(latitude, longitude) == "Niks Gevonden")) {
                        naamLand = getLandNaam(latitude, longitude)
                        var eersteKeer = true
                        ingelogdePersoon.landenLijst.forEach {
                            if (it.naam == naamLand) {
                                eersteKeer = false
                            }
                        }
                        var nieuwLand = Land(naamLand, rating, comment, jaar, maand, dag, latitude, longitude, eersteKeer)
                        ingelogdePersoon.voegLandToe(nieuwLand)
                        mainNavigation.saveData();
                        var aantalNieuweAchievements = achievementCheck(ingelogdePersoon)
                        if (aantalNieuweAchievements > 0) {
                            Snackbar
                                .make(
                                    it,
                                    "Je hebt " + aantalNieuweAchievements + " nieuwe achievements behaald",
                                    Snackbar.LENGTH_LONG
                                )
                                .show()
                        } else if (nieuwLand.eersteKeer) {
                            Snackbar
                                .make(
                                    it,
                                    "Je hebt een nieuw land toegevoegd",
                                    Snackbar.LENGTH_LONG
                                )
                                .show()
                        } else {
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
                else{
                    Snackbar
                        .make(
                            it,
                            "Ongeldige Datum",
                            Snackbar.LENGTH_LONG
                        )
                        .show()

                }
                }
        }

        binding.locatieKnop.setOnClickListener {
            mainNavigation.getLocatie()

            var latitude: Double? = mainNavigation.latitude
            var longitude: Double? = mainNavigation.longitude

            if (latitude == null && longitude == null) {
                Snackbar.make(
                    it,
                    "Locatie opvragen niet toegestaan",
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
            else {
                Snackbar
                    .make(
                        it,
                        latitude.toString() + "    " + longitude.toString(),
                        Snackbar.LENGTH_LONG
                    )
                    .show()

                if (latitude != null && longitude != null && !(getLandNaam(latitude, longitude) == "Niks Gevonden")) {
                    binding.landNaam.setText(getLandNaam(latitude, longitude))
                } else {
                    Snackbar
                        .make(
                            it,
                            "Land Niet Gevonden",
                            Snackbar.LENGTH_LONG
                        )
                        .show()
                }
            }
        }
        return binding.root
    }

    private fun getLandNaam(latitude : Double, longitude : Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        var naamLand:String
        if (!(addresses.size == 0)){
            naamLand = addresses[0].countryName
            return naamLand
        }
        return "Niks Gevonden"
    }
    private fun achievementCheck(persoon:Persoon): Int {
        var achievements1 = persoon.behaaldeAchievements.size
        persoon.achievementCheck()
        var achievements2 = persoon.behaaldeAchievements.size
        return (achievements2 - achievements1)
    }
}