package com.example.mpproject

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mpproject.adapters.ParkAdapter
import com.example.mpproject.data.Park
import com.example.mpproject.databinding.ActivityMainBinding
import com.example.mpproject.parse.parseParkData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import com.example.mpproject.data.Park.parkList
import com.example.mpproject.database.ParkDatabase
import com.example.mpproject.interfaces.ParkApiService
import com.example.mpproject.models.ParkItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val spinnerData = listOf("업데이트 순", "혼잡도 낮은 순", "혼잡도 높은 순")
    private var isBookmarkSelected = false
    private var bookmarkList: List<ParkItem> = listOf()
    private var searchList: List<ParkItem> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

                            var db: ParkDatabase? = null
                            var isBookmarked: Boolean
                            db = ParkDatabase.getInstance(binding.root.context)
                            CoroutineScope(Dispatchers.Main).launch {
                                async(Dispatchers.IO) {
                                    isBookmarked = db!!.parkDao().getBookmark(data[0])
                                    foundItem?.let {
                                        it.congestLevel = data[1]
                                        it.temp = data[2]
                                        it.minTemp = data[3]
                                        it.maxTemp = data[4]
                                        it.skyStatus = data[6]
                                        it.isBookmarked = isBookmarked
                                    }
                                        ?: parkList.add(
                                            ParkItem(
                                                data[0],
                                                data[1],
                                                data[2],
                                                data[3],
                                                data[4],
                                                data[5],
                                                data[6],
                                                isBookmarked
                                            )
                                        )
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

        // toolbar
        setSupportActionBar(binding.toolbar)

        // recycler view
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
        binding.recyclerView.adapter = ParkAdapter(parkList)

        // spinner
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
    }

    // toolbar option menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val menuSearch = menu.findItem(R.id.action_search)
        val searchView = menuSearch?.actionView as? SearchView
        val bookmarkItem = binding.toolbar.menu.findItem(R.id.action_bookmark)


        menuSearch.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                // 검색 버튼 눌렸을 때
                isBookmarkSelected = false
                bookmarkItem.setIcon(R.drawable.unselected_bookmark)
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                // 뒤로가기 버튼 눌렸을 때
                binding.recyclerView.adapter = ParkAdapter(parkList)
                isBookmarkSelected = false
                bookmarkItem.setIcon(R.drawable.unselected_bookmark)
                return true
            }
        })

//        searchView?.isSubmitButtonEnabled = true

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // text submit
                Log.d("Search", "검색: $query")
                if(isBookmarkSelected) {
                    searchList = bookmarkList.filter { parkItem ->
                        parkItem.name?.contains(query.orEmpty(), ignoreCase = true) == true
                    }
                    binding.recyclerView.adapter = ParkAdapter(searchList)
                } else {
                    searchList =  parkList.filter { parkItem ->
                        parkItem.name?.contains(query.orEmpty(), ignoreCase = true) == true
                    }
                    binding.recyclerView.adapter = ParkAdapter(searchList)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // text change
                if(isBookmarkSelected) {
                    val searchList = bookmarkList.filter { parkItem ->
                        parkItem.name?.contains(newText.orEmpty(), ignoreCase = true) == true
                    }
                    binding.recyclerView.adapter = ParkAdapter(searchList)
                }
                else {
                    val searchList =  parkList.filter { parkItem ->
                        parkItem.name?.contains(newText.orEmpty(), ignoreCase = true) == true
                    }
                    binding.recyclerView.adapter = ParkAdapter(searchList)
                }


                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_bookmark -> {
                isBookmarkSelected = !isBookmarkSelected

                if (isBookmarkSelected) {
                    item.setIcon(R.drawable.selected_bookmark)
                    bookmarkList = parkList.filter { it.isBookmarked }
                    binding.recyclerView.adapter = ParkAdapter(bookmarkList)
                } else {
                    item.setIcon(R.drawable.unselected_bookmark)
                    binding.recyclerView.adapter = ParkAdapter(parkList)
                }


                return true
            }
            else -> return true
        }
    }


//    override fun onResume() {
//        super.onResume()
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://openapi.seoul.go.kr:8088/")
//            .build()
//
//        val service = retrofit.create(ParkApiService::class.java)
//
//        Park.parks.forEach { park ->
//            GlobalScope.launch(Dispatchers.IO) {
//                try {
//                    val response: Response<ResponseBody> = service.getParkData(park)
//
//                    if (response.isSuccessful) {
//                        val responseBody: ResponseBody? = response.body()
//                        if (responseBody != null) {
//                            val data = parseParkData(responseBody)
//
//                            // Find the ParkItem with matching name and update its properties
//                            val foundItem = parkList.find { it.name == data[0] }
//
//                            var db: ParkDatabase? = null
//                            var isBookmarked: Boolean
//                            db = ParkDatabase.getInstance(binding.root.context)
//                            CoroutineScope(Dispatchers.Main).launch {
//                                async(Dispatchers.IO) {
//                                    isBookmarked = db!!.parkDao().getBookmark(data[0])
//                                    foundItem?.let {
//                                        it.congestLevel = data[1]
//                                        it.temp = data[2]
//                                        it.minTemp = data[3]
//                                        it.maxTemp = data[4]
//                                        it.skyStatus = data[6]
//                                        it.isBookmarked = isBookmarked
//                                    }
//                                    ?: parkList.add(
//                                        ParkItem(
//                                            data[0],
//                                            data[1],
//                                            data[2],
//                                            data[3],
//                                            data[4],
//                                            data[5],
//                                            data[6],
//                                            isBookmarked
//                                        )
//                                    )
//                                }
//                            }
//
//                            Log.d("API_NEW", data.toString())
//                            Log.d("LIST_SIZE", parkList.size.toString())
//
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
//    }
}


