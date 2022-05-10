package com.example.appshitaroundtheworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appshitaroundtheworld.databinding.ActivityMainNavigationBinding
import com.example.appshitaroundtheworld.fragments.*
import com.example.appshitaroundtheworld.model.Land
import com.example.appshitaroundtheworld.model.Persoon

class MainNavigation : AppCompatActivity() {

    private lateinit var binding: ActivityMainNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)

        val ingelogdePersoon: Persoon = intent.getSerializableExtra("ingelogdePersoon") as Persoon
        val sharedData = ingelogdePersoon
        val homeFragment = HomeFragment(sharedData)
        val nieuwLandFragment = NieuwLandFragment(sharedData)
        val prestatiesFragment = PrestatiesFragment(sharedData)
        val vorigeBestemmingFragment = VorigeBestemmingFragment(sharedData)
        val mapsFragment = MapsFragment(sharedData)


        binding = ActivityMainNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.home.setOnClickListener {
            switchTo(homeFragment)
        }
        binding.nieuwLand.setOnClickListener {
            switchTo(nieuwLandFragment)
        }
        binding.prestaties.setOnClickListener {
            switchTo(prestatiesFragment)
        }
        binding.vorigeBestemmingen.setOnClickListener {
            switchTo(vorigeBestemmingFragment)
        }
        binding.maps.setOnClickListener {
            switchTo(mapsFragment)
        }
        switchTo(homeFragment)
    }

    private fun switchTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            // if you want to add it to the "back stack" to support the back button, also call this.
            addToBackStack("Fragment_${fragment.id}")
            commit()
        }
    }
}
