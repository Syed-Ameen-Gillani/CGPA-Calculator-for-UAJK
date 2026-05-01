package com.syed.cgpacalculator.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

object AdsReward {
    private const val TAG = "AdsReward"
    private const val AD_UNIT_ID = "ca-app-pub-4100399028713117/5586391835"
    
    private var rewardedAd: RewardedAd? = null
    private var isLoading = false
    private var isShowing = false

    fun initialize(context: Context) {
        MobileAds.initialize(context) {
            Log.d(TAG, "AdMob Initialized")
            preload(context)
        }
    }

    fun preload(context: Context) {
        if (isLoading || rewardedAd != null) return

        isLoading = true
        val adRequest = AdRequest.Builder().build()
        
        RewardedAd.load(context, AD_UNIT_ID, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, "Ad failed to load: ${adError.message}")
                rewardedAd = null
                isLoading = false
            }

            override fun onAdLoaded(ad: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                rewardedAd = ad
                isLoading = false
            }
        })
    }

    fun showIfAvailable(activity: Activity, onRewardEarned: () -> Unit) {
        if (rewardedAd == null) {
            Log.d(TAG, "Ad not ready yet.")
            preload(activity)
            onRewardEarned() // Continue if no ad is available to avoid breaking the app
            return
        }

        if (isShowing) return

        if (!AdsPolicy.shouldAttemptAd(activity)) {
            Log.d(TAG, "Ad suppressed by policy.")
            onRewardEarned()
            return
        }

        var rewardGranted = false

        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad dismissed fullscreen content.")
                rewardedAd = null
                isShowing = false
                
                if (rewardGranted) {
                    AdsPolicy.recordAdShown(activity)
                    onRewardEarned()
                } else {
                    Log.d(TAG, "User dismissed ad before earning reward.")
                    android.widget.Toast.makeText(
                        activity,
                        "Please watch the full ad to see your result",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
                preload(activity)
            }

            override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                Log.e(TAG, "Ad failed to show: ${adError.message}")
                rewardedAd = null
                isShowing = false
                onRewardEarned() // Fallback: show result if ad fails to show
                preload(activity)
            }

            override fun onAdImpression() {
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                isShowing = true
            }
        }

        rewardedAd?.show(activity) { rewardItem ->
            rewardGranted = true
            Log.d(TAG, "User earned the reward: ${rewardItem.amount} ${rewardItem.type}")
        }
    }
}
