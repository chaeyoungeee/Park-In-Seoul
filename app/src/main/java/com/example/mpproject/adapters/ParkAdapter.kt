package com.example.mpproject.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mpproject.DetailActivity
import com.example.mpproject.R
import com.example.mpproject.databinding.ItemParkBinding
import com.example.mpproject.models.ParkItem
import java.time.LocalTime

class ParkAdapter(val parkList: List<ParkItem>) : RecyclerView.Adapter<ParkAdapter.Holder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkAdapter.Holder {
        val binding = ItemParkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: ParkAdapter.Holder, position: Int) {
        holder.name.text = parkList[position].name
        holder.congestLevel.text = parkList[position].congestLevel
//      holder.temp.text = parkList[position].temp + "℃"
        holder.minTemp.text = parkList[position].minTemp + "℃"
        holder.maxTemp.text = parkList[position].maxTemp + "℃"

        val img = parkList[position].code.lowercase()
        val resourceId = holder.itemView.context.resources.getIdentifier(img, "drawable", holder.itemView.context.packageName)
        holder.parkImg.setImageResource(resourceId)

        val currentTime = LocalTime.now()
        val hour = currentTime.hour

        Log.d("h", hour.toString())

        when (parkList[position].skyStatus) {
            "맑음" -> {
                when (hour) {
                    in 6..19 -> holder.skyStatus.text = "☀️"
                    else -> holder.skyStatus.text = "🌙"
                }
            }
            "구름 많음" -> {
                when (hour) {
                    in 6..19 -> holder.skyStatus.text = "⛅️"
                    else -> holder.skyStatus.text = "🌥️"
                }
            }
            "흐림" -> {
                holder.skyStatus.text = "☁️"
            }
        }
        when (parkList[position].congestLevel) {
            "여유" -> holder.congestCircle.setTextColor(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.green
                )
            )
            "보통" -> holder.congestCircle.setTextColor(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.yellow
                )
            )
            "약간 붐빔" -> holder.congestCircle.setTextColor(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.orange
                )
            )
            "붐빔" -> holder.congestCircle.setTextColor(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.red
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return parkList.size
    }

    inner class Holder(val binding: ItemParkBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.name
        val parkImg = binding.parkImg
        val congestLevel = binding.congestLevel
        val congestCircle = binding.congestCircle
//      val temp = binding.temp
        val skyStatus = binding.skyStatus
        val minTemp = binding.minTemp
        val maxTemp = binding.maxTemp

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                val clickedPark = parkList[position]

                val intent = Intent(binding.root.context, DetailActivity::class.java)

                intent.putExtra("name", clickedPark.name)
                intent.putExtra("congestLevel", clickedPark.congestLevel)
                intent.putExtra("img", parkList[position].code.lowercase())
                binding.root.context.startActivity(intent)
            }
        }
    }
}