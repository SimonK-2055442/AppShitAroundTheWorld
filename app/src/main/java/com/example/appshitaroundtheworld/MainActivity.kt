package com.example.appshitaroundtheworld

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appshitaroundtheworld.databinding.ActivityMainBinding
import com.example.appshitaroundtheworld.model.Persoon
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var gebruikersLijst: MutableList<Persoon> = mutableListOf()
        val Sander = Persoon("Sander", "DamnSun")
        val Simon = Persoon("Simon", "IkBenGay")
        gebruikersLijst.add(Sander)
        gebruikersLijst.add(Simon)
        var ingelogdePersoon: Persoon = Persoon("","")

        binding.logIn.setOnClickListener {

            var naam = binding.naam.text.toString()
            var code = binding.code.text.toString()
            var inSysteem: Boolean = false

            gebruikersLijst.forEach() { Persoon ->
                if (naam.contentEquals(Persoon.naam) && code.contentEquals(Persoon.code)) {
                    inSysteem = true
                    ingelogdePersoon = Persoon
                    val intent = Intent(this, MainNavigation::class.java)
                    intent.putExtra("ingelogdePersoon", ingelogdePersoon)
                    startActivity(intent)
                }
            }
            if (!inSysteem) {
                Snackbar
                    .make(it, "fout", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        binding.signIn.setOnClickListener {
            var naam = binding.naam.text.toString()
            var code = binding.code.text.toString()

            //inhoud.forEach() { Persoon ->
            //if (!naam.contentEquals(Persoon.naam)) {
            var persoon = Persoon(naam, code)
            gebruikersLijst.add(persoon)
            //}
            /*else{
                Snackbar
                    .make(it, "fout", Snackbar.LENGTH_LONG)
                    .show()
            }

        }*/
        }
    }
}