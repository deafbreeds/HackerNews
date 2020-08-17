package com.deafbreeds.hackernews.util

import android.content.Context
import com.deafbreeds.hackernews.R
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Hours

object TimeUtil {
    fun getPostTime(context: Context, dateTime: DateTime): String {
        val now = DateTime()
        val hour = Hours.hoursBetween(dateTime, now).hours

        return when {
            dateTime.isAfter(now) || hour == 0 -> context.resources.getQuantityString(
                R.plurals.hour_ago,
                1,
                1
            )
            hour in 1..23 -> context.resources.getQuantityString(R.plurals.hour_ago, hour, hour)
            else -> {
                val days = Days.daysBetween(dateTime, now).days
                context.resources.getQuantityString(R.plurals.day_ago, days, days)
            }
        }
    }
}