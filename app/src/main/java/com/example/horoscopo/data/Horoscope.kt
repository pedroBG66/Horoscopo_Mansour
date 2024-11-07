package com.example.horoscopo.data

import com.example.horoscopo.R

class Horoscope (val id: String, val name: Int, val dates: Int, val image: Int, val planet: Int, val element: Element) {
    fun getElementStringRes(): Int {
        return when (element) {
            Element.FIRE -> R.string.horoscope_element_fire
            Element.EARTH ->  R.string.horoscope_element_earth
            Element.AIR -> R.string.horoscope_element_air
            Element.WATER -> R.string.horoscope_element_water
        }
    }

    fun getElementColorRes(): Int {
        return when (element) {
            Element.FIRE -> R.color.fire
            Element.EARTH ->  R.color.earth
            Element.AIR -> R.color.air
            Element.WATER -> R.color.water
        }
    }

    enum class Element {
        FIRE, EARTH, AIR, WATER
    }
}