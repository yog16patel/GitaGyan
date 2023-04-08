package com.yogi.gitagyan.ui.util

import com.yogi.gitagyan.models.SlokUi

fun List<SlokUi>.mapToSlokaNumberWithTheAppSlokaIndex(selectedSlok: Int): Int {

    this.forEachIndexed { index, slokUi ->
        run {
            val slokNumberStr = slokUi.slokaNumber.split(" ")[1]
            val dashIndex = slokNumberStr.indexOf("-")
            if (dashIndex > 0) {
                val slokCombinedNumbers = slokNumberStr.split('-')
                val start: Int = slokCombinedNumbers[0].toInt()
                val end: Int = slokCombinedNumbers[1].toInt()
                if (selectedSlok in start..end) {
                    return index
                }
            }
            else{
                val number = slokNumberStr.toInt()
                if(number == selectedSlok) return index
            }
        }
    }
    return 1
}

fun List<SlokUi>.mapToSlokaIndexToAppSlokaNumber(selectedSlokIndex: Int): Int {


    val slokUi = if(selectedSlokIndex >= size)this[size-1] else this[selectedSlokIndex]

    val slokNumberStr = slokUi.slokaNumber.split(" ")[1]
    val dashIndex = slokNumberStr.indexOf("-")
    return if (dashIndex > 0) {
        val slokCombinedNumbers = slokNumberStr.split('-')
        slokCombinedNumbers[0].toInt()

    }
    else{
        slokNumberStr.toInt()
    }

}
