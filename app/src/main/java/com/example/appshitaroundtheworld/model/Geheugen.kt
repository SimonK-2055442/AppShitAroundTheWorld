package com.example.appshitaroundtheworld.model

import android.content.SharedPreferences
import com.google.gson.Gson
import com.example.appshitaroundtheworld.model.Persoon

class Geheugen {
    val gson = Gson()
    var gebruikersLijst = mutableListOf<Persoon>()

    private fun saveGebruikersLijst(persoon: Persoon, sharedPreferences: SharedPreferences) {
        val opTeSlaanPersoon = gson.toJson(persoon)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString(persoon.naam, opTeSlaanPersoon)
        }.apply()
    }

    private fun saveNamenLijst(lijst: String, sharedPreferences: SharedPreferences){
        val editor = sharedPreferences.edit()
        editor.apply{
            putString("Lijstnaam", lijst)
        }.apply()
    }

    fun opslaan(sharedPreferences: SharedPreferences, sharedPreferencesNamenlijst : SharedPreferences, gebruikersLijst : MutableList<Persoon>){
        var namenlijst = ""
        gebruikersLijst.forEach() { Persoon ->
            saveGebruikersLijst(Persoon, sharedPreferences)
            namenlijst = namenlijst + Persoon.naam + "/"
        }
        saveNamenLijst(namenlijst, sharedPreferencesNamenlijst)
    }
    fun opslaanIngelogdePersoon(sharedPreferences: SharedPreferences, sharedPreferencesNamenlijst : SharedPreferences, ingelogdePersoon: Persoon){
        getData(sharedPreferences,sharedPreferencesNamenlijst, gebruikersLijst)
        var teVerwijderenPeroon : Persoon = Persoon("","")
        gebruikersLijst.forEach{
            if(ingelogdePersoon.naam.contentEquals(it.naam)){
                teVerwijderenPeroon = it
            }
        }
        if (gebruikersLijst.contains(teVerwijderenPeroon)) {
            gebruikersLijst.remove(teVerwijderenPeroon)
        }
        gebruikersLijst.add(ingelogdePersoon)
        opslaan(sharedPreferences, sharedPreferencesNamenlijst, gebruikersLijst)
        var treu = gebruikersLijst.contains(ingelogdePersoon)
    }

    fun getData(sharedPreferences: SharedPreferences,  sharedPreferencesNamenlijst : SharedPreferences, gebruikersLijst : MutableList<Persoon>){
        val savedNamenLijst = sharedPreferencesNamenlijst.getString("Lijstnaam", null)
        var namenString: List<String> = savedNamenLijst!!.split("/")
        namenString.forEach() {
            if (!(it == "")) {
                val savedString = sharedPreferences.getString(it, null)
                val gebruiker = gson.fromJson(savedString, Persoon::class.java)
                gebruikersLijst.add(gebruiker)
            }
        }
        /*val editor = sharedPreferences.edit()
        editor.apply{
            editor.clear()
        }.apply()

        val editors = sharedPreferencesNamenlijst.edit()
        editors.apply{
            editors.clear()
        }.apply()*/
    }
}