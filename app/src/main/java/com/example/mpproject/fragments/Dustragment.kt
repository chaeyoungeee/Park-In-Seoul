package com.example.mpproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mpproject.databinding.FragmentDustBinding
import com.github.mikephil.charting.BuildConfig


class Dustragment : Fragment() {
    private var _binding: FragmentDustBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentDustBinding.inflate(inflater, container, false)

        binding.airIdx.text = arguments?.getString("airIdx")
        binding.airMsg.text = arguments?.getString("airMsg")
        binding.pm10Idx.text = arguments?.getString("pm10Idx")
        binding.pm10.text = "(" + arguments?.getString("pm10") + "㎍/㎡)"
        binding.pm10Idx.text = arguments?.getString("pm25Idx")
        binding.pm25.text = "(" + arguments?.getString("pm25") + "㎍/㎡)"
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}