package com.example.appshitaroundtheworld

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appshitaroundtheworld.databinding.ActivityMainBinding
import com.example.appshitaroundtheworld.model.Persoon
import com.google.android.material.snackbar.Snackbar
import android.content.SharedPreferences
import com.google.gson.Gson
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var gebruikersLijst: MutableList<Persoon> = mutableListOf()
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var ingelogdePersoon: Persoon = Persoon("", "")
        //getData()

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

            if (!alInSysteem(naam, gebruikersLijst)) {
                var persoon = Persoon(naam, code)
                gebruikersLijst.add(persoon)
            } else {
                Snackbar
                    .make(it, "Al in systeem", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun saveData(persoon: Persoon, sharedPreferences:SharedPreferences) {
        val opTeSlaanPersoon = gson.toJson(persoon)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString(persoon.naam, opTeSlaanPersoon)
        }.apply()
    }

    private fun getData(){
        val sharedPreferencesNamenlijst = getSharedPreferences("namenlijst", Context.MODE_PRIVATE)
        val sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val savedNamenLijst = sharedPreferencesNamenlijst.getString("Lijstnaam", null)

        var namenString: List<String> = savedNamenLijst!!.split("/")
        namenString.forEach() {
            if (!(it == "")) {
                val savedString = sharedPreferences.getString(it, null)
                val gebruiker = gson.fromJson(savedString, Persoon::class.java)
                gebruikersLijst.add(gebruiker)
            }
        }
    }

    private fun saveNamenLijst(lijst: String, sharedPreferences: SharedPreferences){
        val editor = sharedPreferences.edit()
        editor.apply{
            putString("Lijstnaam", lijst )
        }.apply()
    }

    private fun opslaan(){
        val sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val sharedPreferencesNamenlijst = getSharedPreferences("namenlijst", Context.MODE_PRIVATE)
        var namenlijst = ""
        gebruikersLijst.forEach() { Persoon ->
            saveData(Persoon, sharedPreferences)
            namenlijst = namenlijst + Persoon.naam + "/"
        }
        saveNamenLijst(namenlijst, sharedPreferencesNamenlijst)
    }

    override fun onDestroy(){
        opslaan()
        super.onDestroy()
    }
}

    private fun alInSysteem(naam: String, gebruikersLijst: MutableList<Persoon>):Boolean {
        gebruikersLijst.forEach() { Persoon ->
            if (naam.contentEquals(Persoon.naam)) {
                return true
            }
    }
        return false
}