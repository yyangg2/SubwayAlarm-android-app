package com.example.subway_alarm.data.api

import com.example.subway_alarm.models.ViewModelImpl
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ApiThread(
    val viewModel: ViewModelImpl, val stationApi: StationApi
) : Thread() {
    var urlBuilder: StringBuilder
    var url: URL
    val sb: java.lang.StringBuilder

    init {
        urlBuilder =
            StringBuilder("http://swopenAPI.seoul.go.kr/api/subway/7a5370504e6868743131324443656265/xml/realtimeStationArrival/0/1/서울") /*URL*/
        url = URL(urlBuilder.toString())
        sb = StringBuilder()
    }

    fun getApiString(): String? {
        return this.sb.toString()
    }

    fun setStationName(stationName: String) {
        urlBuilder =
            StringBuilder("http://swopenAPI.seoul.go.kr/api/subway/7a5370504e6868743131324443656265/xml/realtimeStationArrival/0/1/$stationName") /*URL*/
        url = URL(urlBuilder.toString())
    }


    override fun run() {

        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("Content-type", "application/xml")
        println("Response code: " + conn.responseCode.toString()) /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/

        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        val rd: BufferedReader = if (conn.responseCode in 200..300) {
            BufferedReader(InputStreamReader(conn.inputStream))
        } else {
            BufferedReader(InputStreamReader(conn.errorStream))
        }
        var line: String?
        while (rd.readLine().also { line = it } != null) {
            sb.append(line)
        }
        rd.close()
        conn.disconnect()

        //데이터 파싱
        //완료된 데이터를 storage에 저장
        stationApi.setStringApiData(sb.toString())
        stationApi.setParseApiData(sb.toString())
    }
}