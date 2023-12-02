package com.example.mpproject

import com.example.mpproject.data.Park.parkList
import com.example.mpproject.data.Park.parks
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.mpproject.database.ParkDatabase
import com.example.mpproject.databinding.ActivitySplashBinding
import com.example.mpproject.interfaces.ParkApiService
import com.example.mpproject.models.ParkItem
import com.example.mpproject.parse.parseParkData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit

class SplashActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://openapi.seoul.go.kr:8088/")
            .build()

        val service = retrofit.create(ParkApiService::class.java)

        parks.subList(0, 5).forEach { park ->
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response: Response<ResponseBody> = service.getParkData(park)

                    if (response.isSuccessful) {
                        val responseBody: ResponseBody? = response.body()
                        if (responseBody != null) {
                            val data = parseParkData(responseBody)
                            var db: ParkDatabase? = null
                            var isBookmarked: Boolean
                            db = ParkDatabase.getInstance(binding.root.context)
                            CoroutineScope(Dispatchers.Main).launch {
                                async(Dispatchers.IO) {
                                    isBookmarked = db!!.parkDao().getBookmark(data[0])
                                    parkList.add(ParkItem(data[0], data[1], data[2], data[3], data[4], data[5], data[6], isBookmarked))
                                }
                            }

                            Log.d("INIT_LIST_SIZE", parkList.size.toString())
                        }
                    }
                } catch (e: Exception) {
                    Log.e("API", e.toString())
                } finally {
                    if (parkList.size == 5) {
                        launch(Dispatchers.Main) {
                            Handler().postDelayed({
                                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                startActivity(intent)
                                finish()
                            }, DURATION)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val DURATION : Long = 10
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}