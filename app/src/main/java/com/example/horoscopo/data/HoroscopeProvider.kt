package com.example.horoscopo.data

import com.example.horoscopo.R

class HoroscopeProvider {
    companion object {
        private val horoscopeList: List<Horoscope> = listOf(
            Horoscope("aries", R.string.horoscope_name_aries, R.string.horoscope_date_aries, R.drawable.aries_icon),
            Horoscope("aries", R.string.horoscope_name_aries, R.string.horoscope_date_aries, R.drawable.aries_icon),
            Horoscope("aries", R.string.horoscope_name_aries, R.string.horoscope_date_aries, R.drawable.aries_icon),
            Horoscope("aries", R.string.horoscope_name_aries, R.string.horoscope_date_aries, R.drawable.aries_icon)
        )

        fun findAll() : List<Horoscope> {
            return horoscopeList
        }

        fun findById(id: String) : Horoscope {
            return horoscopeList.find { it.id == id }!!
        }
    }
}