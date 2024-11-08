package com.example.horoscopo.data

import com.example.horoscopo.R
import com.example.horoscopo.data.Horoscope.Element

class HoroscopeProvider {
    companion object {
        private val horoscopeList: List<Horoscope> = listOf(
            Horoscope("aries", R.string.horoscope_name_aries, R.string.horoscope_date_aries, R.drawable.aries_icon, R.string.horoscope_planet_aries, Element.FIRE),
            Horoscope("taurus", R.string.horoscope_name_taurus, R.string.horoscope_date_taurus, R.drawable.taurus_icon, R.string.horoscope_planet_taurus, Element.EARTH),
            Horoscope("gemini", R.string.horoscope_name_gemini, R.string.horoscope_date_gemini, R.drawable.gemini_icon, R.string.horoscope_planet_gemini, Element.AIR),
            Horoscope("cancer", R.string.horoscope_name_cancer, R.string.horoscope_date_cancer, R.drawable.cancer_icon, R.string.horoscope_planet_cancer, Element.WATER),
            Horoscope("leo", R.string.horoscope_name_leo, R.string.horoscope_date_leo, R.drawable.leo_icon, R.string.horoscope_planet_leo, Element.FIRE),
            Horoscope("virgo", R.string.horoscope_name_virgo, R.string.horoscope_date_virgo, R.drawable.virgo_icon, R.string.horoscope_planet_virgo, Element.EARTH),
            Horoscope("libra", R.string.horoscope_name_libra, R.string.horoscope_date_libra, R.drawable.libra_icon, R.string.horoscope_planet_libra, Element.AIR),
            Horoscope("scorpio", R.string.horoscope_name_scorpio, R.string.horoscope_date_scorpio, R.drawable.scorpio_icon, R.string.horoscope_planet_scorpio, Element.WATER),
            Horoscope("sagittarius", R.string.horoscope_name_sagittarius, R.string.horoscope_date_sagittarius, R.drawable.sagittarius_icon, R.string.horoscope_planet_sagittarius, Element.FIRE),
            Horoscope("capricorn", R.string.horoscope_name_capricorn, R.string.horoscope_date_capricorn, R.drawable.capricorn_icon, R.string.horoscope_planet_capricorn, Element.EARTH),
            Horoscope("aquarius", R.string.horoscope_name_aquarius, R.string.horoscope_date_aquarius, R.drawable.aquarius_icon, R.string.horoscope_planet_aquarius, Element.AIR),
            Horoscope("pisces", R.string.horoscope_name_pisces, R.string.horoscope_date_pisces, R.drawable.pisces_icon, R.string.horoscope_planet_pisces, Element.WATER)
        )

        fun findAll() : List<Horoscope> {
            return horoscopeList
        }

        fun findById(id: String) : Horoscope {
            return horoscopeList.find { it.id == id }!!
        }

        fun getNextHoroscope(currentId: String): Horoscope {
            val currentIndex = horoscopeList.indexOfFirst { it.id == currentId }
            val nextIndex = (currentIndex + 1) % horoscopeList.size
            return horoscopeList[nextIndex]
        }

        fun getPreviousHoroscope(currentId: String): Horoscope {
            val currentIndex = horoscopeList.indexOfFirst { it.id == currentId }
            val prevIndex = if (currentIndex - 1 < 0) horoscopeList.size - 1 else currentIndex - 1
            return horoscopeList[prevIndex]
        }
    }
}