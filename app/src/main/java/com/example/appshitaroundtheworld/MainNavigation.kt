package com.example.appshitaroundtheworld

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.appshitaroundtheworld.databinding.ActivityMainNavigationBinding
import com.example.appshitaroundtheworld.fragments.*
import com.example.appshitaroundtheworld.model.Persoon
import com.example.appshitaroundtheworld.model.Geheugen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar

class MainNavigation : AppCompatActivity() {

    private lateinit var binding: ActivityMainNavigationBinding
    private lateinit var menuBarToggle: ActionBarDrawerToggle
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var geheugen: Geheugen = Geheugen()
    var ingelogdePersoon: Persoon = Persoon("","")
    var latitude: Double? = null
    var longitude: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        ingelogdePersoon = intent.getSerializableExtra("ingelogdePersoon") as Persoon
        val sharedData = ingelogdePersoon
        val homeFragment = HomeFragment(sharedData)
        val nieuwLandFragment = NieuwLandFragment(sharedData,this)
        val prestatiesFragment = PrestatiesFragment(sharedData)
        val vorigeBestemmingFragment = VorigeBestemmingFragment(sharedData)
        val mapsFragment = MapsFragment(sharedData)


        binding = ActivityMainNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuBarToggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.menu_open, R.string.menu_close)
        binding.drawerLayout.addDrawerListener(menuBarToggle)
        menuBarToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> switchTo(homeFragment)
                R.id.nav_nieuweBestemming -> switchTo(nieuwLandFragment)
                R.id.nav_prestaties -> switchTo(prestatiesFragment)
                R.id.nav_vorigeBestemmingen ->  switchTo(vorigeBestemmingFragment)
                R.id.nav_maps -> switchTo(mapsFragment)
            }
            true
        }
        switchTo(homeFragment)
    }

    fun getLocatie(){
        val task = fusedLocationProviderClient.lastLocation

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 101)
        }
        task.addOnSuccessListener { if(it != null){
            latitude = it.latitude
            longitude = it.longitude
        }
        }
    }

    private fun switchTo(fragment: Fragment) {
            binding.drawerLayout.closeDrawer(binding.navView)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, fragment)
                addToBackStack("Fragment_${fragment.id}")
                commit()
            }
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            if(menuBarToggle.onOptionsItemSelected(item)) {
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    fun saveData(){
        val sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val sharedPreferencesNamenlijst = getSharedPreferences("namenlijst", Context.MODE_PRIVATE)
        geheugen.opslaanIngelogdePersoon(sharedPreferences, sharedPreferencesNamenlijst, ingelogdePersoon)
    }

}
