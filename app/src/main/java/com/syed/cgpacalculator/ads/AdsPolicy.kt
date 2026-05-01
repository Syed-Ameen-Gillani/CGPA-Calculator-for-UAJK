package com.syed.cgpacalculator.ads

import android.content.Context
import android.content.SharedPreferences

object AdsPolicy {
    private const val COOLDOWN_SECONDS = 60
    private const val LAUNCH_GRACE_SECONDS = 10
    private var appLaunchTime = System.currentTimeMillis()

    fun init() {
        appLaunchTime = System.currentTimeMillis()
    }

    fun canShowNow(context: Context): Boolean {
        val now = System.currentTimeMillis()
        
        // Check launch grace period
        if (now - appLaunchTime < LAUNCH_GRACE_SECONDS * 1000) {
            return false
        }

        // Check cooldown from SharedPreferences
        val prefs = context.getSharedPreferences("ads_prefs", Context.MODE_PRIVATE)
        val lastShown = prefs.getLong("last_ad_shown_time", 0)
        
        return now - lastShown >= COOLDOWN_SECONDS * 1000
    }

    fun recordAdShown(context: Context) {
        val prefs = context.getSharedPreferences("ads_prefs", Context.MODE_PRIVATE)
        prefs.edit().putLong("last_ad_shown_time", System.currentTimeMillis()).apply()
    }

    /**
     * Logic to decide if we should even attempt to show an ad.
     * Can be expanded with more complex logic if needed.
     */
    fun shouldAttemptAd(context: Context): Boolean {
        return canShowNow(context)
    }
}
