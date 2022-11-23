package com.example.weatherapp.presentation.main_page.components.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.SelectCountryItemBinding
import com.example.weatherapp.domain.model.Country

class SelectCountryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var cityList : MutableList<Country> = mutableListOf()

    interface OnItemClickListener {
        fun onItemClick(
            position: Int,
            city : Country
        )
    }

    private lateinit var mListener  : OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.select_country_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder){
            val city = cityList[position]
            holder.binding.apply {
                cityTv.text = city.city
                zoneTv.text = city.zone
            }
            holder.itemView.setOnClickListener {
                mListener.onItemClick(position , city)
            }
        }
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    fun setCityList(list: MutableList<Country>){
        this.cityList = list
    }

    private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = SelectCountryItemBinding.bind(view)
    }
}