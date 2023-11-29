package com.example.mpproject.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpproject.adapters.EventAdapter
import com.example.mpproject.adapters.WeatherAdapter
import com.example.mpproject.data.DataWeather
import com.example.mpproject.databinding.FragmentWeatherBinding
import com.example.mpproject.models.EventItem
import com.example.mpproject.models.WeatherItem


class WeatherFragment : Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private var fcstWeatherList: List<WeatherItem>? = null
//    = listOf(
//
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)

        val fcstWeather = arguments?.getSerializable("fcstWeather") as DataWeather?

        fcstWeatherList = (fcstWeather?.fcstWeather as? List<WeatherItem>) ?: listOf()

//        val newWeatherItem = WeatherItem(
//            DT = "시간",
//            temp = "기온",
//            preciptation = "강수량(mm)",
//            preceptType = "강수형태",
//            rainChance = "강수확률",
//            skyStatus = "날씨"
//        )

//        fcstWeatherList?.add(0, newWeatherItem)
        Log.d("f", fcstWeatherList.toString())

        binding.currentTemp.text = arguments?.getString("temp") + "℃/"
        binding.minTemp.text = arguments?.getString("minTemp") + "℃/"
        binding.maxTemp.text = arguments?.getString("maxTemp") + "℃"
        binding.sensibleTemp.text = arguments?.getString("sensibleTemp") + "℃"
        binding.precptMsg.text = arguments?.getString("precptMsg")

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = fcstWeatherList?.let { WeatherAdapter(it) }

        return binding.root

    }

}