package com.example.mpproject.data

import android.os.Parcel
import android.os.Parcelable
import com.example.mpproject.models.ParkItem
import java.io.Serializable

object Park {
     val parks = listOf<String>(
        "강서한강공원",
        "고척돔",
        "광나루한강공원",
        "광화문광장",
        "국립중앙박물관·용산가족공원",
        "난지한강공원",
        "남산공원",
        "노들섬",
        "뚝섬한강공원",
        "망원한강공원",
        "반포한강공원",
        "북서울꿈의숲",
        "불광천",
        "서리풀공원·몽마르뜨공원",
        "서울광장",
        "서울대공원",
        "서울숲공원",
        "아차산",
        "양화한강공원",
        "어린이대공원",
        "여의도한강공원",
        "월드컵공원",
        "응봉산",
        "이촌한강공원",
        "잠실종합운동장",
        "잠실한강공원",
        "잠원한강공원",
        "청계산",
        "청와대")

    val parkList = ArrayList<ParkItem>()
    var newParkList = ArrayList<ParkItem>()
}

data class FCST_PPLTN(
   var time: String,
   var congestionLevel: String,
   var populationMin: String,
   var populationMax: String
)