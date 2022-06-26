package com.kevcordova.jobsitychallenge.utils

import com.example.baseandroidmodulekevcordova.utils.KCalendarUtils
import java.util.*

object DateUtils {
    /**
     * Applied on dateString -> 2014-01-31
     * If is required with different format create a base function
     */
    fun convertDateStringInDateFormat(dateString: String, patternFormat: String = "") : String {
        val arrayDate = dateString.split(KCalendarUtils.DIVIDER_DATE_ORIGINAL)
        if (arrayDate.size < 3) return ""
        val calendar = Calendar.getInstance()
        calendar.run {
            set(Calendar.DAY_OF_MONTH, arrayDate[2].toInt())
            set(Calendar.MONTH, arrayDate[1].toInt())
            set(Calendar.YEAR, arrayDate[0].toInt())
        }
        return KCalendarUtils.formatDateWithPattern(
            calendar,
            if (patternFormat.isEmpty())
                "${KCalendarUtils.PATTERN_DATE_DAY} ${KCalendarUtils.PATTERN_DATE_MONTH_NAME} ${KCalendarUtils.PATTERN_DATE_YEAR_COMPLETE}"
            else
                patternFormat
        )
    }
}