package com.weather.forecast.ui.landing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.weather.forecast.BR
import com.weather.forecast.R
import com.weather.forecast.data.LandingData

class LandingAdapter(private val landingData: List<LandingData>) :
    RecyclerView.Adapter<LandingAdapter.LandingViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandingViewHolder {
        return LandingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_landing_adapter, parent, false
            )
        )
    }

    override fun getItemCount() = landingData.size

    override fun onBindViewHolder(holder: LandingViewHolder, position: Int) {
        holder.updateAdapter(landingData[position])
    }

    class LandingViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateAdapter(item: LandingData) {
            binding.setVariable(BR.obj, item)
            binding.executePendingBindings()
        }
    }
}