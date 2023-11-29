package com.example.mpproject.fragments

import android.R
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mpproject.data.DataPopulation
import com.example.mpproject.data.FCST_PPLTN
import com.example.mpproject.databinding.FragmentCongestBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter


class CongestFragment : Fragment() {
    private var barChart: BarChart? = null
    private var _binding: FragmentCongestBinding? = null
    private val binding get() = _binding!!
    var fcstPopulationList: List<FCST_PPLTN>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCongestBinding.inflate(inflater, container, false)

        val congestMsg = arguments?.getString("congestMsg")
        if (congestMsg != "") {
            if (congestMsg != null) {
                binding.congestMsg1.text = "📌 " + congestMsg.split(".")[0].toString() + "."
                binding.congestMsg2.text = "📌 " + congestMsg.substringAfter(congestMsg.split(".")[0].toString() + ". ")
            }
        } else {
            binding.title.text = "인구 전망"
            binding.congestMsg1.visibility = View.GONE
            binding.congestMsg2.visibility = View.GONE
        }

        val fcstPopulation = arguments?.getSerializable("fcstPopulation") as DataPopulation?

        fcstPopulationList = (fcstPopulation?.fcstPopulation as? List<FCST_PPLTN>)

        if (fcstPopulationList?.size  != 0) {
            val entries = ArrayList<BarEntry>()

            barChart = binding.chart as BarChart

            val barData = BarData()

            fcstPopulation?.let { populationList ->
                for (i in 0 until 9) {
                    val populationMin = fcstPopulationList?.get(i)?.populationMin?.toFloat()
                    val populationMax = fcstPopulationList?.get(i)?.populationMax?.toFloat()
                    val populationAvg = (populationMin?.plus(populationMax!!))?.div(2)
                    populationAvg?.let { BarEntry(i.toFloat(), it) }?.let { entries.add(it) }
                }
            }


            val barDataSet =
                BarDataSet(entries, "bardataset")
            barDataSet.colors = addColors()
            barDataSet.setDrawValues(false)
            barData.addDataSet(barDataSet)

            barChart?.run {
                axisLeft.run {
                    setDrawGridLines(false)
                    setDrawAxisLine(true)
                    axisLineColor = ContextCompat.getColor(context, R.color.white)
                    setDrawLabels(true)
                    textColor = ContextCompat.getColor(context, R.color.black)
                    textSize = 12f
                }
                xAxis.run {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    axisLineColor = ContextCompat.getColor(context, R.color.white)
                    setDrawAxisLine(true)
                    textColor = ContextCompat.getColor(context, R.color.black)
                    valueFormatter = MyXAxisFommater()
                    textSize = 12f
                }


                setExtraOffsets(0f, 0f, 0f, 16f) // 하단에 16f의 마진 추가
                axisRight.isEnabled = false
                description.isEnabled = false
                animateY(1000)
                setDrawGridBackground(false)
                legend.isEnabled = false
                this.data = barData
                invalidate()
                setTouchEnabled(false)
            }
        }
        else {
            binding.chart.visibility = View.GONE
            binding.title.text = "실시간 인구 정보"
        }

        if (fcstPopulationList?.size  == 0 && congestMsg == "") {
            binding.title.text = "인구 정보 데이터가 없습니다."
        }


        return binding.root
    }
    fun addColors(): List<Int> {
        val colors = mutableListOf<Int>()
        for (i in 0 until 9) {
            val congestLevel = fcstPopulationList?.get(i)?.congestionLevel
            when(congestLevel) {
                "여유" -> colors.add(Color.parseColor("#00e31e"))
                "보통" -> colors.add(Color.parseColor("#ffdd00"))
                "약간 붐빔" -> colors.add(Color.parseColor("#ff760d"))
                "붐빔" -> colors.add(Color.parseColor("#ff0008"))
            }
        }
        return colors
    }

    inner class MyXAxisFommater : ValueFormatter() {
        private val labels = setLabels()

        fun setLabels(): MutableList<String> {
            var labels =  mutableListOf<String>()
            for (i in 0 until 9) {
                val time = fcstPopulationList?.get(i)?.time
                if (time != null) {
                    val t1 = time.split(" ")[1]
                    val t2 = t1.split(":")[0]
                    labels.add(t2)
                }
            }
            return labels
        }

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if (index >= 0 && index < labels.size) {
                labels[index]
            } else {
                ""
            }
        }
    }

}