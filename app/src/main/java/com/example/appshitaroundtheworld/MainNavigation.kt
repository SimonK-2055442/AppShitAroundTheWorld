package com.example.appshitaroundtheworld

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.appshitaroundtheworld.databinding.ActivityMainNavigationBinding
import com.example.appshitaroundtheworld.fragments.*
import com.example.appshitaroundtheworld.model.Persoon
import com.example.appshitaroundtheworld.model.Geheugen

class MainNavigation : AppCompatActivity() {

    private lateinit var binding: ActivityMainNavigationBinding
    private lateinit var menuBarToggle: ActionBarDrawerToggle
    var geheugen: Geheugen = Geheugen()
    var ingelogdePersoon: Persoon = Persoon("","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)

        ingelogdePersoon = intent.getSerializableExtra("ingelogdePersoon") as Persoon
        val sharedData = ingelogdePersoon
        val homeFragment = HomeFragment(sharedData)
        val nieuwLandFragment = NieuwLandFragment(sharedData)
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
    override fun onDestroy(){
        val sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val sharedPreferencesNamenlijst = getSharedPreferences("namenlijst", Context.MODE_PRIVATE)
        //geheugen.opslaanIngelogdePersoon(sharedPreferences, sharedPreferencesNamenlijst, ingelogdePersoon)
        super.onDestroy()
    }

        private fun switchTo(fragment: Fragment) {
            val sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
            val sharedPreferencesNamenlijst = getSharedPreferences("namenlijst", Context.MODE_PRIVATE)
            //geheugen.opslaanIngelogdePersoon(sharedPreferences, sharedPreferencesNamenlijst, ingelogdePersoon)

            binding.drawerLayout.closeDrawer(binding.navView)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, fragment)
                // if you want to add it to the "back stack" to support the back button, also call this.
                addToBackStack("Fragment_${fragment.id}")
                commit()
            }
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // we need to do this to respond correctly to clicks on menu items, otherwise it won't be caught
            if(menuBarToggle.onOptionsItemSelected(item)) {
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }
