package com.example.prathamchikitse.data

import com.example.prathamchikitse.model.Emergency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonParser {

    fun parseEmergencyList(json: String): List<Emergency> {
        val type = object : TypeToken<List<Emergency>>() {}.type
        return Gson().fromJson(json, type)
    }
}