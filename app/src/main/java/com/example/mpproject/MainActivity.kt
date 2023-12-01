package com.example.mpproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mpproject.adapters.ParkAdapter
import com.example.mpproject.data.Park
import com.example.mpproject.data.Park.newParkList
import com.example.mpproject.databinding.ActivityMainBinding
import com.example.mpproject.parse.parseParkData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import com.example.mpproject.data.Park.parkList
import com.example.mpproject.interfaces.ParkApiService
import com.example.mpproject.models.ParkItem

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var isSortdBtnClick = false
    private var isSortaBtnClick = false
    private val spinnerData = listOf("업데이트 순", "혼잡도 낮은 순", "혼잡도 높은 순")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://openapi.seoul.go.kr:8088/")
//            .build()
//
//        val service = retrofit.create(ParkApiService::class.java)
//        Park.parks.subList(8, 29).forEach { park ->
//            GlobalScope.launch(Dispatchers.IO) {
//                try {
//                    val response: Response<ResponseBody> = service.getParkData(park)
//
//                    if (response.isSuccessful) {
//                        val responseBody: ResponseBody? = response.body()
//                        if (responseBody != null) {
//                            val data = parseParkData(responseBody)
//                            parkList.add(
//                                ParkItem(
//                                    data[0],
//                                    data[1],
//                                    data[2],
//                                    data[3],
//                                    data[4],
//                                    data[5],
//                                    data[6]
//                                )
//                            )
//                            Log.d("API", "결과: $parkList")
//                            launch(Dispatchers.Main) {
//                                binding.recyclerView.adapter?.notifyDataSetChanged()
//                            }
//                        }
//                    }
//                } catch (e: Exception) {
//                    Log.e("API", e.toString())
//                }
//            }
//        }
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
        binding.recyclerView.adapter = ParkAdapter(parkList)

        var spinnerAdapter = ArrayAdapter<String>(this, R.layout.spinner_item, spinnerData)
        binding.spinner.adapter = spinnerAdapter
        binding.spinner.setSelection(0)
        binding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position) {
                    0 -> {
                        binding.recyclerView.adapter = ParkAdapter(parkList)
                    }
                    1 -> {
                        val sortedParkList = parkList.sortedBy {
                            when (it.congestLevel) {
                                "여유" -> 1
                                "보통" -> 2
                                "약간 붐빔" -> 3
                                "붐빔" -> 4
                                else -> 5
                            }
                        }
                        binding.recyclerView.adapter = ParkAdapter(sortedParkList.toMutableList())
                    }
                    2 -> {
                        val sortedParkList = parkList.sortedBy {
                            when (it.congestLevel) {
                                "붐빔" -> 1
                                "약간 붐빔" -> 2
                                "보통" -> 3
                                "여유" -> 4
                                else -> 5
                            }
                        }
                        binding.recyclerView.adapter = ParkAdapter(sortedParkList.toMutableList())
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

//        binding.sortdBtn.setOnClickListener {
//            isSortdBtnClick = !isSortdBtnClick
//
//            if (isSortdBtnClick) {
//                if (isSortaBtnClick) {
//                    isSortaBtnClick = !isSortaBtnClick
//                    binding.sortaBtn.setBackgroundResource(R.drawable.black_button)
////                    binding.sortaBtn.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
//                }
//                binding.sortdBtn.setBackgroundResource(R.drawable.white_button)
////                binding.sortdBtn.setTextColor(ContextCompat.getColor(this, R.color.black))
//
//                val sortedParkList = parkList.sortedBy {
//                    when (it.congestLevel) {
//                        "여유" -> 1
//                        "보통" -> 2
//                        "약간 붐빔" -> 3
//                        "붐빔" -> 4
//                        else -> 5
//                    }
//                }
//                binding.recyclerView.adapter = ParkAdapter(sortedParkList.toMutableList())
//            } else {
//                binding.sortdBtn.setBackgroundResource(R.drawable.black_button)
////                binding.sortdBtn.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
//                binding.recyclerView.adapter = ParkAdapter(parkList)
//            }
//
//        }
//
//        binding.sortaBtn.setOnClickListener {
//            isSortaBtnClick = !isSortaBtnClick
//
//            if (isSortaBtnClick) {
//                if (isSortdBtnClick) {
//                    isSortdBtnClick = !isSortdBtnClick
//                    binding.sortdBtn.setBackgroundResource(R.drawable.black_button)
////                    binding.sortdBtn.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
//                }
//                binding.sortaBtn.setBackgroundResource(R.drawable.white_button)
//                binding.sortaBtn.setTextColor(ContextCompat.getColor(this, R.color.black))
//
//                val sortedParkList = parkList.sortedBy {
//                    when (it.congestLevel) {
//                        "붐빔" -> 1
//                        "약간 붐빔" -> 2
//                        "보통" -> 3
//                        "여유" -> 4
//                        else -> 5
//                    }
//                }
//                binding.recyclerView.adapter = ParkAdapter(sortedParkList.toMutableList())
//            } else {
//                binding.sortaBtn.setBackgroundResource(R.drawable.black_button)
////                binding.sortaBtn.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
//                binding.recyclerView.adapter = ParkAdapter(parkList)
//            }
//
//        }
    }

    override fun onResume() {
        super.onResume()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://openapi.seoul.go.kr:8088/")
            .build()

        val service = retrofit.create(ParkApiService::class.java)

        Park.parks.forEach { park ->
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response: Response<ResponseBody> = service.getParkData(park)

                    if (response.isSuccessful) {
                        val responseBody: ResponseBody? = response.body()
                        if (responseBody != null) {
                            val data = parseParkData(responseBody)

                            // Find the ParkItem with matching name and update its properties
                            val foundItem = parkList.find { it.name == data[0] }

                            if (foundItem == null) {
                                parkList.add(
                                    ParkItem(
                                        data[0],
                                        data[1],
                                        data[2],
                                        data[3],
                                        data[4],
                                        data[5],
                                        data[6]
                                    )
                                )
                            } else {
                                foundItem.let {
                                    it.congestLevel = data[1]
                                    it.temp = data[2]
                                    it.minTemp = data[3]
                                    it.maxTemp = data[4]
                                    it.skyStatus = data[6]
                                }
                            }
                            Log.d("API_NEW", data.toString())
                            Log.d("LIST_SIZE", parkList.size.toString())

                            launch(Dispatchers.Main) {
                                binding.recyclerView.adapter?.notifyDataSetChanged()
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("API", e.toString())
                }
            }
        }
    }
}


