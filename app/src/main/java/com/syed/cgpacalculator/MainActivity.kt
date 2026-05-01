package com.syed.cgpacalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.syed.cgpacalculator.data.HistoryItem
import com.syed.cgpacalculator.data.UserDataManager
import com.syed.cgpacalculator.data.UserProfile
import com.syed.cgpacalculator.ui.screens.*
import com.syed.cgpacalculator.ui.theme.CGPAclaculatorTheme
import com.syed.cgpacalculator.ads.AdsReward
import com.syed.cgpacalculator.ads.AdsPolicy
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        AdsPolicy.init()
        AdsReward.initialize(this)
        
        setContent {
            CGPAclaculatorTheme {
                AppNavigation()
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Dashboard : Screen("dashboard")
    object AddResult : Screen("add_result")
    object Cgpa : Screen("cgpa")
    object Conversion : Screen("conversion")
    object History : Screen("history")
    object PrivacyPolicy : Screen("privacy_policy")
}

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val dataManager = remember { UserDataManager(context) }
    val profile = remember { mutableStateOf(dataManager.getProfile()) }
    
    val navController = rememberNavController()
    
    val startDestination = if (profile.value.name.isBlank()) {
        Screen.Onboarding.route
    } else {
        Screen.Dashboard.route
    }
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onComplete = { name, dept ->
                    dataManager.saveProfile(UserProfile(name, dept))
                    profile.value = dataManager.getProfile()
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                onBack = if (navController.previousBackStackEntry != null) {
                    { navController.popBackStack() }
                } else null,
                initialName = profile.value.name,
                initialDept = profile.value.department
            )
        }
        composable(Screen.Dashboard.route) {
            val history = dataManager.getHistory()
            val latestCgpa = history
                .filter { it.type == "CGPA" }
                .lastOrNull()?.result?.toDoubleOrNull() ?: 0.0
                
            DashboardScreen(
                profile = profile.value,
                latestCgpa = latestCgpa,
                onAddResult = { navController.navigate(Screen.AddResult.route) },
                onViewHistory = { navController.navigate(Screen.History.route) },
                onSwitchDept = {
                    navController.navigate(Screen.Onboarding.route)
                },
                onCalculateCgpa = { navController.navigate(Screen.Cgpa.route) },
                onConversion = { gpaMode ->
                    navController.navigate("${Screen.Conversion.route}?gpaMode=$gpaMode")
                },
                onViewPrivacyPolicy = {
                    navController.navigate(Screen.PrivacyPolicy.route)
                }
            )
        }
        composable(Screen.AddResult.route) {
            AddResultScreen(
                onBack = { navController.popBackStack() },
                onSave = { title, result, type ->
                    val history = dataManager.getHistory().toMutableList()
                    history.add(HistoryItem(title, result, java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date()), type))
                    dataManager.saveHistory(history)
                }
            )
        }
        composable(Screen.Cgpa.route) {
            CgpaCalculatorScreen(
                onBack = { navController.popBackStack() },
                onSave = { title, result, type ->
                    val history = dataManager.getHistory().toMutableList()
                    history.add(
                        HistoryItem(
                            title,
                            result,
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                            type
                        )
                    )
                    dataManager.saveHistory(history)
                }
            )
        }
        composable(
            route = "${Screen.Conversion.route}?gpaMode={gpaMode}",
            arguments = listOf(
                navArgument("gpaMode") { defaultValue = "false" }
            )
        ) { backStackEntry ->
            val gpaMode = backStackEntry.arguments?.getString("gpaMode") == "true"
            ConversionScreen(
                onBack = { navController.popBackStack() },
                startWithGpaToMarks = gpaMode
            )
        }
        composable(Screen.History.route) {
            val history = dataManager.getHistory()
            HistoryScreen(
                history = history,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.PrivacyPolicy.route) {
            PrivacyPolicyScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
