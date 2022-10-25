package com.example.subway_alarm.data.repository

interface StationRepository {
    fun refreshStations()

    fun getStationArrivals(stationName: String): String

    fun updateStations()
}