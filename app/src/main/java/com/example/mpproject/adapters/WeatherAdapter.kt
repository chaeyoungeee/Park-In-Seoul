package com.example.mpproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mpproject.databinding.ItemWeatherBinding
import com.example.mpproject.models.WeatherItem

class WeatherAdapter(val weatherList: List<WeatherItem>) : RecyclerView.Adapter<WeatherAdapter.Holder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapter.Holder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: WeatherAdapter.Holder, position: Int) {
        holder.DT.text = weatherList[position].DT.substring(8, 10) + "시"
        holder.temp.text = weatherList[position].temp + "℃"
        if (weatherList[position].preciptation == "-") {
            holder.preciptation.text = weatherList[position].preciptation
        } else {
            holder.preciptation.text = weatherList[position].preciptation + "mm"
        }
//        holder.preceptType.text = weatherList[position].preceptType
        holder.rainChance.text = weatherList[position].rainChance + "%"
        holder.skyStatus.text = weatherList[position].skyStatus



        when(weatherList[position].preceptType) {
            "눈" -> {
                holder.skyStatus.text = "🌨️"
            }
            "비" -> {
                holder.skyStatus.text = "🌧️"
            }
            else -> {
                when (weatherList[position].skyStatus) {
                    "맑음" -> {
                        when (weatherList[position].DT.substring(8, 10).toInt()) {
                            in 6..19 -> holder.skyStatus.text = "☀️"
                            else -> holder.skyStatus.text = "🌙"
                        }
                    }
                    "구름많음" -> {
                        when (weatherList[position].DT.substring(8, 10).toInt()) {
                            in 6..19 -> holder.skyStatus.text = "🌤️"
                            else -> holder.skyStatus.text = "🌥️"
                        }
                    }
                    "흐림" -> {
                        holder.skyStatus.text = "☁️"
                    }
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return weatherList.size
    }

    inner class Holder(val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
        val DT = binding.DT
        val temp = binding.temp
        val preciptation = binding.preciptation
//        val preceptType = binding.preceptType
        val rainChance = binding.rainChance
        val skyStatus = binding.skyStatus
    }
}