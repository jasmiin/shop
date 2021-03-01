package com.mesada.shop.helper

import java.util.*

object RandomNumber {
    fun generateID(): Long {
        val rnd = Random()
        val digits = CharArray(11)
        digits[0] = (rnd.nextInt(9) + '1'.toInt()).toChar()
        for (i in 1 until digits.size) {
            digits[i] = (rnd.nextInt(10) + '0'.toInt()).toChar()
        }
        return String(digits).toLong()
    }
}