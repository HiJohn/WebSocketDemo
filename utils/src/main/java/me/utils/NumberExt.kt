package me.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log10
import kotlin.math.pow



/**
 * 单位：分
 */
fun Long.formatMoney(): String {
//    if (this % 100 == 0L) {
//        return (this / 100).toString()
//    }

    return Formatter().format("%.2f", this * 0.01F).toString()
}

fun Double.cutToTwo():String{
    return Formatter().format("%.2f", this).toString()
}

fun Int.formatMoney(): String {
    return Formatter().format("%.2f", this * 0.01F).toString()
}

fun Long.toFileSize(): String {
    if (this <= 0) return "0B"
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (Math.log10(this.toDouble()) / Math.log10(1024.0)).toInt()
    return String.format("%.2f%s", this / Math.pow(1024.0, digitGroups.toDouble()), units[digitGroups])
}

/**
 * 保留n位小数 for float
 */
fun Float.keepDecimal(DecimalLength: Int, roundingMode: RoundingMode = RoundingMode.FLOOR): Float {
    var decimalFormatPattern = "0."
    for (index in 1..DecimalLength) {
        decimalFormatPattern += "#"
    }
    val format = DecimalFormat(decimalFormatPattern)
    format.roundingMode = roundingMode
    return format.format(this).toFloat()
}

/**
 * 保留n位小数 for double
 */
fun Double.keepDecimal(
    DecimalLength: Int,
    roundingMode: RoundingMode = RoundingMode.FLOOR
): Double {
    var decimalFormatPattern = "0."
    for (index in 1..DecimalLength) {
        decimalFormatPattern += "#"
    }
    val format = DecimalFormat(decimalFormatPattern)
    format.roundingMode = roundingMode
    return format.format(this).toDouble()
}

fun Long.formatDateFromLong(): String {
    val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US)
    val dateTime = Date(this)
    return format.format(dateTime)
}

fun Long.formatDateYear(): String {
    val format = SimpleDateFormat("yyyy/MM/dd", Locale.US)
    val dateTime = Date(this)
    return format.format(dateTime)
}

fun Long.formatLongSize(): String {
    if (this <= 0) {
        return "0 B"
    }

    val units = arrayOf("B", "kB", "MB", "GB", "TB")
    val digitGroups = (log10(toDouble()) / log10(1024.0)).toInt()
    return "${
        DecimalFormat("#,##0.#").format(
            this / 1024.0.pow(digitGroups.toDouble())
        )
    } ${units[digitGroups]}"
}

fun Long.formatSize(): Array<String> {

    if (this <= 0) {
        return arrayOf("0", "B")
    }

    val units = arrayOf("B", "kB", "MB", "GB", "TB")
    val digitGroups = (log10(toDouble()) / log10(1024.0)).toInt()

    return arrayOf(
        DecimalFormat("#,##0.#").format(
            this / 1024.0.pow(digitGroups.toDouble())
        ), units[digitGroups]
    )

}

fun Int.formatIntSize(): String {
    if (this <= 0) {
        return "0 B"
    }

    val units = arrayOf("B", "kB", "MB", "GB", "TB")
    val digitGroups = (log10(toDouble()) / log10(1024.0)).toInt()
    return "${
        DecimalFormat("#,##0.#").format(
            this / 1024.0.pow(digitGroups.toDouble())
        )
    } ${units[digitGroups]}"
}

fun Long.formatSizeThousand(): String {
    if (this <= 0) {
        return "0 B"
    }

    val units = arrayOf("B", "kB", "MB", "GB", "TB")
    val digitGroups = (log10(toDouble()) / log10(1000.0)).toInt()
    return "${
        DecimalFormat("#,##0.##").format(
            this / 1000.0.pow(digitGroups.toDouble())
        )
    } ${units[digitGroups]}"
}

