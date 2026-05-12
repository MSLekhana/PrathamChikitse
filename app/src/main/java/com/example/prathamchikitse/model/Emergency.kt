package com.example.prathamchikitse.model

data class Emergency(
    val id: Int,
    val name_en: String,
    val name_kn: String,
    val steps_en: List<String>,
    val steps_kn: List<String>,
    val dos_en: List<String>,
    val dos_kn: List<String>,
    val donts_en: List<String>,
    val donts_kn: List<String>
)