package com.example.appshitaroundtheworld.fragments

import android.graphics.Color
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appshitaroundtheworld.R
import com.example.appshitaroundtheworld.databinding.FragmentPrestatiesBinding
import com.example.appshitaroundtheworld.model.Persoon

class PrestatiesFragment(val ingelogdePersoon: Persoon) : Fragment(R.layout.fragment_prestaties) {

    private lateinit var binding: FragmentPrestatiesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrestatiesBinding.inflate(layoutInflater)

        ingelogdePersoon.achievementCheck()
        var behaaldeAchievements: ArrayList<Int> = ingelogdePersoon.behaaldeAchievements

        for (i in behaaldeAchievements){
            if (i == 1){
                binding.textAch1.setTextColor(Color.GREEN)
            }
            if (i == 2){
                binding.textAch2.setTextColor(Color.GREEN)
            }
            if (i == 3){
                binding.textAch3.setTextColor(Color.GREEN)
            }
            if (i == 4){
                binding.textAch4.setTextColor(Color.GREEN)
            }
            if (i == 5){
                binding.textAch5.setTextColor(Color.GREEN)
            }
            if (i == 6){
                binding.textAch6.setTextColor(Color.GREEN)
            }
        }
        return binding.root
    }
}
