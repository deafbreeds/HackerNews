package com.deafbreeds.hackernews

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.deafbreeds.hackernews.util.TimeUtil
import org.joda.time.DateTime
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimeUtilTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    @Test
    fun oneHourAgo(){
        val oneHourAgo = DateTime().minusHours(1)

        val result = TimeUtil.getPostTime(context, oneHourAgo)
        Assert.assertEquals(result, context.resources.getQuantityString(R.plurals.hour_ago, 1, 1))
    }

    @Test
    fun withinOneHour(){
        val withinOneHour = DateTime().minusMinutes(30)

        val result = TimeUtil.getPostTime(context, withinOneHour)
        Assert.assertEquals(result, context.resources.getQuantityString(R.plurals.hour_ago, 1, 1))
    }

    @Test
    fun afterCurrentTime(){
        val afterThreeHours = DateTime().plusHours(3)

        val result = TimeUtil.getPostTime(context, afterThreeHours)
        Assert.assertEquals(result, context.resources.getQuantityString(R.plurals.hour_ago, 1, 1))
    }

    @Test
    fun withinOneDay(){
        val withinOneDay = DateTime().minusHours(23)

        val result = TimeUtil.getPostTime(context, withinOneDay)
        Assert.assertEquals(result, context.resources.getQuantityString(R.plurals.hour_ago, 23, 23))
    }

    @Test
    fun oneDayAgo(){
        val oneDayAgo = DateTime().minusDays(1)

        val result = TimeUtil.getPostTime(context, oneDayAgo)
        Assert.assertEquals(result, context.resources.getQuantityString(R.plurals.day_ago, 1, 1))
    }

    @Test
    fun manyDayAgo(){
        val threeDaysAgo = DateTime().minusDays(3)

        val result = TimeUtil.getPostTime(context, threeDaysAgo)
        Assert.assertEquals(result, context.resources.getQuantityString(R.plurals.day_ago, 3, 3))
    }
}