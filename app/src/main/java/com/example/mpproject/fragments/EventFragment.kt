package com.example.mpproject.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mpproject.adapters.EventAdapter
import com.example.mpproject.data.DataEvent
import com.example.mpproject.databinding.FragmentEventBinding
import com.example.mpproject.models.EventItem


class EventFragment : Fragment() {
    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    var eventList: List<EventItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentEventBinding.inflate(inflater, container, false)

        val event = arguments?.getSerializable("event") as DataEvent?

        eventList = (event?.event as? List<EventItem>)

        if (eventList?.get(0)?.name != "") {
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
            binding.recyclerView.adapter = eventList?.let { EventAdapter(it) }
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.info.visibility = View.VISIBLE
        }



        return binding.root
    }

}