package com.example.weatherapp.presentation.main_page.components

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.common.Constants
import com.example.weatherapp.databinding.SelectCountryBottomSheetBinding
import com.example.weatherapp.domain.model.Country
import com.example.weatherapp.presentation.main_page.components.adapter.SelectCountryAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.roundToLong

class SelectCountryBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: SelectCountryBottomSheetBinding
    private lateinit var adapter : SelectCountryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectCountryBottomSheetBinding.inflate(inflater, container, false)
        adapter = SelectCountryAdapter()
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter.setCityList(Constants.cityList)
        binding.countriesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.countriesRv.setHasFixedSize(true)
        binding.countriesRv.adapter = adapter
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickListener(object  : SelectCountryAdapter.OnItemClickListener{
            override fun onItemClick(position: Int, city: Country) {
                val sharedPreference =  requireContext().getSharedPreferences("Country-Zone",
                    Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                var cityZone = "${city.zone}/${city.city}"
                editor.putString("City" ,cityZone )
                editor.putLong("Long" , city.long.roundToLong())
                editor.putLong("Lat" , city.lat.roundToLong())
                editor.commit()
                Toast.makeText(requireContext() , "City changed." , Toast.LENGTH_SHORT).show()
                this@SelectCountryBottomSheet.dismiss()
            }
        })

    }

}