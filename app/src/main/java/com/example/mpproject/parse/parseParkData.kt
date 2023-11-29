package com.example.mpproject.parse
import android.util.Xml
import okhttp3.ResponseBody
import org.xmlpull.v1.XmlPullParser
import java.io.StringReader

suspend fun parseParkData(responseBody: ResponseBody): List<String> {
    val xml = responseBody.string()

    val parser = Xml.newPullParser()
    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
    parser.setInput(StringReader(xml))

    var eventType = parser.eventType
    val skyStatusList = mutableListOf<String>()
    var name = ""
    var congestLevel = ""
    var temp = ""
    var minTemp = ""
    var maxTemp = ""
    var code = ""

    while (eventType != XmlPullParser.END_DOCUMENT) {
        when (eventType) {
            XmlPullParser.START_TAG -> {
                when (parser.name) {
                    "SKY_STTS" -> {
                        parser.next()
                        val skyStatus = parser.text
                        skyStatusList.add(skyStatus)
                    }
                    "AREA_NM" -> {
                        parser.next()
                        name = parser.text
                    }
                    "AREA_CONGEST_LVL" -> {
                        parser.next()
                        congestLevel = parser.text
                    }
                    "TEMP" -> {
                        parser.next()
                        temp = parser.text
                    }
                    "MIN_TEMP" -> {
                        parser.next()
                        minTemp = parser.text
                    }
                    "MAX_TEMP" -> {
                        parser.next()
                        maxTemp = parser.text
                    }
                    "AREA_CD" -> {
                        parser.next()
                        code = parser.text
                    }
                }
            }
        }
        eventType = parser.next()
    }

    return listOf(name, congestLevel, temp, minTemp, maxTemp, code, skyStatusList.firstOrNull().toString())
}
