package com.example.mpproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mpproject.databinding.ItemEventBinding
import com.example.mpproject.databinding.ItemParkBinding
import com.example.mpproject.models.EventItem

class EventAdapter(val eventList: List<EventItem>) : RecyclerView.Adapter<EventAdapter.Holder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.Holder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: EventAdapter.Holder, position: Int) {
        holder.name.text = eventList[position].name
        holder.period.text = eventList[position].period
        holder.place.text = eventList[position].place
    }
    override fun getItemCount(): Int {
        return eventList.size
    }

    inner class Holder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.name
        val period = binding.period
        val place = binding.place
    }
}