package com.example.mpproject

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.mpproject.adapters.ViewPagerAdapter
import com.example.mpproject.data.DataEvent
import com.example.mpproject.data.DataPopulation
import com.example.mpproject.data.DataWeather
import com.example.mpproject.databinding.ActivityDetailBinding
import com.example.mpproject.interfaces.ParkApiService
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import parseParkDetailData
import retrofit2.Response
import retrofit2.Retrofit
import java.io.Serializable

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val name = intent.getStringExtra("name")
        binding.parkName.text = name
        val congestLevel = intent.getStringExtra("congestLevel")
        binding.congestLevel.text = congestLevel
        val img = intent.getStringExtra("img")
        val resourceId = resources.getIdentifier(img, "drawable", packageName)
        binding.parkImg.setImageResource(resourceId)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://openapi.seoul.go.kr:8088/")
            .build()

        val service = retrofit.create(ParkApiService::class.java)

        binding.progressBar.visibility = View.VISIBLE



        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response: Response<ResponseBody> = service.getParkData(name.toString())

                if (response.isSuccessful) {
                    val responseBody: ResponseBody? = response.body()
                    if (responseBody != null) {
                        val data = parseParkDetailData(responseBody)
                        Log.d("event api", "결과: ${data}")

                        lifecycleScope.launch(Dispatchers.Main) {
                            binding.sunrize.text = data["sunrize"]?.toString()
                            binding.sunset.text = data["sunset"]?.toString()


                            val congestBundle = Bundle().apply {
                                putSerializable(
                                    "fcstPopulation",
                                    DataPopulation(data["fcstPopulation"])
                                )
                                putString("congestMsg", data["congestMsg"].toString())
                            }

                            val weatherBundle = Bundle().apply {
                                putSerializable("fcstWeather", DataWeather(data["fcstWeather"]))
                                putString("temp", data["temp"].toString())
                                putString("maxTemp", data["maxTemp"].toString())
                                putString("minTemp", data["minTemp"].toString())
                                putString("sensibleTemp", data["sensibleTemp"].toString())
                                putString("precptMsg", data["precptMsg"].toString())
                            }

                            val dustBundle = Bundle().apply {
                                putString("airMsg", data["airMsg"].toString())
                                putString("airIdx", data["airIdx"].toString())
                                putString("pm10", data["pm10"].toString())
                                putString("pm10Idx", data["pm10Idx"].toString())
                                putString("pm25", data["pm25"].toString())
                                putString("pm25Idx", data["pm25Idx"].toString())
                            }

                            val eventBundle = Bundle().apply {
                                putSerializable("event", DataEvent(data["event"]))
                            }

                            // Adapter에 각 Fragment에 전달할 Bundle 전달
                            val adapter = ViewPagerAdapter(
                                this@DetailActivity,
                                congestBundle,
                                weatherBundle,
                                dustBundle,
                                eventBundle
                            )
                            viewPager.adapter = adapter


                            val tabLayoutTextArray = listOf("혼잡도", "날씨", "미세먼지", "행사")

                            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                                tab.text = tabLayoutTextArray[position]
                            }.attach()

                            binding.progressBar.visibility = View.GONE
                            binding.sunInfo.visibility = View.VISIBLE
                            binding.viewPager.visibility = View.VISIBLE
                        }

                    }
                }
            } catch (e: Exception) {
                Log.e("event api", e.toString())

            } finally {

// tab layout
            }
        }

        tabLayout = binding.tabLayout
        viewPager = binding.viewPager

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        val congestBundle = Bundle().apply {
        }

        val weatherBundle = Bundle().apply {
        }

        val dustBundle = Bundle().apply {
        }

        val eventBundle = Bundle().apply {
        }

        // Adapter에 각 Fragment에 전달할 Bundle 전달
        val adapter = ViewPagerAdapter(
            this@DetailActivity,
            congestBundle,
            weatherBundle,
            dustBundle,
            eventBundle
        )
        viewPager.adapter = adapter


        val tabLayoutTextArray = listOf("혼잡도", "날씨", "미세먼지", "행사")

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabLayoutTextArray[position]
        }.attach()

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}