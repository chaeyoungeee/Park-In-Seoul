import android.util.Xml
import com.example.mpproject.data.FCST_PPLTN
import com.example.mpproject.models.EventItem
import com.example.mpproject.models.WeatherItem
import okhttp3.ResponseBody
import org.xmlpull.v1.XmlPullParser

suspend fun parseParkDetailData(responseBody: ResponseBody): Map<Any, Any> {
    data class FCST_24(
        var DT:String,
        var temp: String,
        var preciptaiton: String,
        var preceptType: String,
        var rainChance: String,
        var skyStatus: String,
    )


//    val xml = responseBody.string()

    val parser = Xml.newPullParser()
    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
//    parser.setInput(StringReader(xml))
    parser.setInput(responseBody.charStream())

    var eventType = parser.eventType

    var congestMsg = ""
    var maxTemp = ""
    var minTemp = ""
    var sensibleTemp = ""
    var sunrize = ""
    var sunset  = ""
    var pm25Idx = ""
    var pm25 = ""
    var pm10Idx = ""
    var pm10 = ""
    var temp = ""
    var precptMsg = ""
    var airMsg = ""
    var airIdx = ""


    val fcstPopulation = mutableListOf<FCST_PPLTN>()
    val fcstWeather = mutableListOf<WeatherItem>()
    val event = mutableListOf<EventItem>()

    while (eventType != XmlPullParser.END_DOCUMENT) {
        when (eventType) {
            XmlPullParser.START_TAG -> {
                when (parser.name) {
                    // 인구 혼잡 정보
                    "AREA_CONGEST_MSG" -> {
                        parser.next()
                        congestMsg = parser.text ?: ""
                    }

                    "FCST_PPLTN" -> {
                        // 새로운 예보 객체를 생성
                        var currentForecast = FCST_PPLTN(
                            time = "",
                            congestionLevel = "",
                            populationMin = "",
                            populationMax = ""
                        )

                        while (!(eventType == XmlPullParser.END_TAG && parser.name == "FCST_PPLTN")) {
                            when (eventType) {
                                XmlPullParser.START_TAG -> {
                                    when (parser.name) {
                                        "FCST_TIME" -> {
                                            parser.next()
                                            currentForecast.time = parser.text ?: ""
                                        }
                                        "FCST_CONGEST_LVL" -> {
                                            parser.next()
                                            currentForecast.congestionLevel = parser.text ?: ""
                                        }
                                        "FCST_PPLTN_MIN" -> {
                                            parser.next()
                                            currentForecast.populationMin = parser.text ?: ""
                                        }
                                        "FCST_PPLTN_MAX" -> {
                                            parser.next()
                                            currentForecast.populationMax = parser.text ?: ""
                                        }
                                    }
                                }
                            }
                            eventType = parser.next()
                        }

                        // forecasts에 현재 예보를 추가
                        fcstPopulation.add(currentForecast)
                    }


                    // 날씨 정보
                    "TEMP" -> {
                        parser.next()
                        temp = parser.text ?: ""
                    }
                    "PCP_MSG" -> {
                        parser.next()
                        precptMsg = parser.text ?: ""
                    }
                    "MAX_TEMP" -> {
                        parser.next()
                        maxTemp = parser.text ?: ""
                    }
                    "MIN_TEMP" -> {
                        parser.next()
                        minTemp = parser.text ?: ""
                    }
                    "SENSIBLE_TEMP" -> {
                        parser.next()
                        sensibleTemp = parser.text ?: ""
                    }
                    "SUNRISE" -> {
                        parser.next()
                        sunrize = parser.text ?: ""
                    }
                    "SUNSET" -> {
                        parser.next()
                        sunset = parser.text ?: ""
                    }
                    "PM25_INDEX" -> {
                        parser.next()
                        pm25Idx = parser.text ?: ""
                    }
                    "PM25" -> {
                        parser.next()
                        pm25 = parser.text ?: ""
                    }
                    "PM10_INDEX" -> {
                        parser.next()
                        pm10Idx = parser.text ?: ""
                    }
                    "PM10" -> {
                        parser.next()
                        pm10 = parser.text ?: ""
                    }
                    "AIR_IDX" -> {
                        parser.next()
                        airIdx = parser.text ?: ""
                    }
                    "AIR_MSG" -> {
                        parser.next()
                        airMsg = parser.text ?: ""
                    }


                    "FCST24HOURS" -> {
                        // 새로운 예보 객체를 생성

                        var currentForecast2 = WeatherItem(
                            DT =  "",
                            temp = "",
                            preciptation = "",
                            preceptType = "",
                            rainChance = "",
                            skyStatus = "",
                        )

                        while (!(eventType == XmlPullParser.END_TAG && parser.name == "FCST24HOURS")) {
                            when (eventType) {
                                XmlPullParser.START_TAG -> {
                                    when (parser.name) {
                                        "FCST_DT" -> {
                                            parser.next()
                                            currentForecast2.DT = parser.text ?: ""
                                        }
                                        "TEMP" -> {
                                            parser.next()
                                            currentForecast2.temp = parser.text ?: ""
                                        }
                                        "PRECIPITATION" -> {
                                            parser.next()
                                            currentForecast2.preciptation = parser.text ?: ""
                                        }
                                        "PRECPT_TYPE" -> {
                                            parser.next()
                                            currentForecast2.preceptType = parser.text ?: ""
                                        }
                                        "RAIN_CHANCE" -> {
                                            parser.next()
                                            currentForecast2.rainChance = parser.text ?: ""
                                        }
                                        "SKY_STTS" -> {
                                            parser.next()
                                            currentForecast2.skyStatus = parser.text ?: ""
                                        }
                                    }
                                }
                            }
                            eventType = parser.next()
                        }

                        fcstWeather.add(currentForecast2)
                    }

                    "EVENT_STTS" -> {
                        var currentEvent = EventItem(
                            name = "",
                            period = "",
                            place = ""
                        )

                        while (!(eventType == XmlPullParser.END_TAG && parser.name == "EVENT_STTS")) {
                            when (eventType) {
                                XmlPullParser.START_TAG -> {
                                    when (parser.name) {
                                        "EVENT_NM" -> {
                                            parser.next()
                                            currentEvent.name = parser.text ?: ""
                                        }
                                        "EVENT_PERIOD" -> {
                                            parser.next()
                                            currentEvent.period = parser.text ?: ""
                                        }
                                        "EVENT_PLACE" -> {
                                            parser.next()
                                            currentEvent.place = parser.text ?: ""
                                        }
                                    }
                                }
                            }
                            eventType = parser.next()
                        }
                        event.add(currentEvent)
                    }

                }
            }
        }
        eventType = parser.next()
    }



    return mapOf(
        "congestMsg" to congestMsg,
        "maxTemp" to maxTemp,
        "minTemp" to minTemp,
        "sensibleTemp" to sensibleTemp,
        "sunrize" to sunrize,
        "sunset" to sunset,
        "pm25Idx" to pm25Idx,
        "pm25" to pm25,
        "pm10Idx" to pm10Idx,
        "pm10" to pm10,
        "temp" to temp,
        "precptMsg" to precptMsg,
        "fcstPopulation" to fcstPopulation,
        "fcstWeather" to fcstWeather,
        "event" to event,
        "airMsg" to airMsg,
        "airIdx" to airIdx
    )
}
